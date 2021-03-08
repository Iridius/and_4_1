package ru.netology.repository

import androidx.lifecycle.LiveData
import ru.netology.dto.Post

class PostRepositorySharedPrefsImpl: PostRepository {
    override fun getAll(): LiveData<List<Post>> {
        TODO("Not yet implemented")
    }

    override fun likeById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun shareById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun viewById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun removeById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun save(post: Post) {
        TODO("Not yet implemented")
    }
}