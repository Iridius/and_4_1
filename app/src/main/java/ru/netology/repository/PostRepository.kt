package ru.netology.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.dto.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun share()
    fun view()
}

class PostRepositoryInMemoryImpl : PostRepository  {
    private var post = Post(id = 0, likes = 10, shares = 997, views = 5, hasAutoLike = false)
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        val direction = if(post.hasAutoLike) -1 else 1

        post = post.copy(hasAutoLike = !post.hasAutoLike, likes = post.likes + 1 * direction)
        data.value = post
    }

    override fun share() {
        post = post.copy(shares = post.shares + 1)
        data.value = post
    }

    override fun view() {
        post = post.copy(views = post.views + 1)
        data.value = post
    }
}