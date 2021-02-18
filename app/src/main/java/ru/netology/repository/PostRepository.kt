package ru.netology.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun viewById(id: Long)
}

class PostRepositoryInMemoryImpl : PostRepository  {
    private var posts = listOf(
        Post(
            id = 0,
            likes = 10,
            shares = 997,
            views = 5,
            hasAutoLike = false,
            title = "Нетология. Университет интернет-профессий",
            subTitle = "21 мая в 18:36",
            content = "Привет! Это новая Нетология. Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению."
        ),
        Post(
            id = 1,
            likes = 16,
            shares = 995,
            views = 99,
            hasAutoLike = false,
            title = "Программирование",
            subTitle = "15 февраля",
            content = "Курсы по веб и мобильной разработке для новичков и junior-разработчиков. Вы освоите профессию разработчика с нуля или добавите в арсенал необходимый язык программирования."
        )
    )

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if(it.id != id) it
            else it.copy(
                hasAutoLike = !it.hasAutoLike,
                likes = it.likes + 1 * (if (it.hasAutoLike) -1 else 1)
            )
        }

        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if(it.id != id) it
            else it.copy(shares = it.shares + 1)
        }
        data.value = posts
    }

    override fun viewById(id: Long) {
        posts = posts.map {
            if(it.id != id) it else it.copy(views = it.views + 1)
        }

        data.value = posts
    }
}