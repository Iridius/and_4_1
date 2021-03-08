package ru.netology.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_post.view.*
import ru.netology.dto.Post
import ru.netology.repository.PostRepository
import ru.netology.repository.PostRepositoryInMemoryImpl
import ru.netology.repository.PostRepositorySharedPrefsImpl

private val emptyPost = Post(
    id = 0,
    likes = 0,
    shares = 0,
    views = 0,
    hasAutoLike = false,
    title = "",
    subTitle = "",
    content = ""
)


class PostViewModel(application: Application) : AndroidViewModel(application) {
    //private val repository: PostRepository = PostRepositoryInMemoryImpl()
    private val repository: PostRepository = PostRepositorySharedPrefsImpl(application)

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

    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if ( it.content == text ) {
                return
            }
            edited.value = it.copy(content = text)
        }
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun viewById(id: Long) = repository.viewById(id)
    fun removeById(id: Long) = repository.removeById(id)
}