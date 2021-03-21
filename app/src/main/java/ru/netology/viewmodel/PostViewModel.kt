package ru.netology.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.db.AppDb
import ru.netology.dto.Post
import ru.netology.repository.PostRepository
import ru.netology.repository.PostRepositoryFileImpl
import ru.netology.repository.PostRepositorySqliteImpl

private val emptyPost = Post(
    id = 0,
    likes = 0,
    shares = 0,
    views = 0,
    hasAutoLike = false,
    title = "",
    subTitle = "",
    content = "",
    link = ""
)


class PostViewModel(application: Application) : AndroidViewModel(application) {
    //private val repository: PostRepositoryFileImpl = PostRepositoryFileImpl(application)
    private val repository: PostRepository = PostRepositorySqliteImpl(
        AppDb.getInstance(application).postDao
    )

    val data = repository.getAll()
    val edited = MutableLiveData(emptyPost)

    fun save() {
        edited.value?.let {
            repository.save(it)
            edited.value = emptyPost
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String?, link: String?) {
        edited.value?.let {
            if ( it.content == content && it.link == link) {
                return
            }
            edited.value = it.copy(content = content.toString(), link = link)
        }
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun viewById(id: Long) = repository.viewById(id)
    fun removeById(id: Long) = repository.removeById(id)
}