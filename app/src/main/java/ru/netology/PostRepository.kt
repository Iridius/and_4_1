package ru.netology

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
}

class PostRepositoryInMemoryImpl : PostRepository  {
    private var post = Post(id = 0, likes = 10, shares = 997, views = 5)
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = post.copy(hasAutoLike = !post.hasAutoLike)
        data.value = post
    }
}