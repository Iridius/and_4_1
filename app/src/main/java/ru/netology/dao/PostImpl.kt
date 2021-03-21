package ru.netology.dao

import ru.netology.dto.Post

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {
    companion object {
        val DDL = """
        CREATE TABLE ${PostColumns.TABLE} (
            ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${PostColumns.COLUMN_TITLE} TEXT NOT NULL,
            ${PostColumns.COLUMN_SUBTITLE} TEXT NOT NULL,
            ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
            ${PostColumns.COLUMN_LINK} TEXT NOT NULL,
            ${PostColumns.COLUMN_HAS_AUTO_LIKE} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_LIKES} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_SHARES} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VIEWS} INTEGER NOT NULL DEFAULT 0
        );
        """.trimIndent()
    }

    object PostColumns {
        const val TABLE = "posts"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_SUBTITLE = "subtitle"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_HAS_AUTO_LIKE = "has_autolike"
        const val COLUMN_LINK = "link"
        const val COLUMN_LIKES = "likes"
        const val COLUMN_SHARES = "shares"
        const val COLUMN_VIEWS = "views"
        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_TITLE,
            COLUMN_SUBTITLE,
            COLUMN_CONTENT,
            COLUMN_HAS_AUTO_LIKE,
            COLUMN_LINK,
            COLUMN_LIKES,
            COLUMN_SHARES,
            COLUMN_VIEWS
        )
    }

    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        ).use {
            while (it.moveToNext()) {
                posts.add(map(it))
            }
        }
        return posts
    }

    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            if (post.id != 0L) {
                put(PostColumns.COLUMN_ID, post.id)
            }
            put(PostColumns.COLUMN_TITLE, post.title)
            put(PostColumns.COLUMN_SUBTITLE, post.subTitle)
            put(PostColumns.COLUMN_CONTENT, post.content)
            put(PostColumns.COLUMN_LINK, post.link)
        }
        val id = db.replace(PostColumns.TABLE, null, values)
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
        ).use {
            it.moveToNext()
            return map(it)
        }
    }

    override fun likeById(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               likes = likes + CASE WHEN has_autoLike THEN -1 ELSE 1 END,
               has_autolike = CASE WHEN has_autolike THEN 0 ELSE 1 END
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun shareById(id: Long) {
        db.execSQL(
            """
           UPDATE posts
           SET likes = likes + 1
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun viewById(id: Long) {
        db.execSQL(
            """
           UPDATE posts
           SET shares = shares + 1
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun removeById(id: Long) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    private fun map(cursor: Cursor): Post {
        with(cursor) {
            return Post(
                id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                hasAutoLike = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_HAS_AUTO_LIKE)) != 0,
                title = getString(getColumnIndexOrThrow(PostColumns.COLUMN_TITLE)),
                subTitle = getString(getColumnIndexOrThrow(PostColumns.COLUMN_SUBTITLE)),
                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
                link = getString(getColumnIndexOrThrow(PostColumns.COLUMN_LINK)),
                likes = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKES)),
                shares = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_SHARES)),
                views = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_VIEWS))
            )
        }
    }
}