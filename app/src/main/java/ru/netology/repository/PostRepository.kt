package ru.netology.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun viewById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
}

class PostRepositoryInMemoryImpl : PostRepository  {
    private var id = 0L
    private var posts = listOf(
        Post(
            id = id++,
            likes = 10,
            shares = 997,
            views = 5,
            hasAutoLike = false,
            title = "Нетология. Университет интернет-профессий",
            subTitle = "21 мая в 18:36",
            content = "Привет! Это новая Нетология. Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению.",
            video = "https://www.youtube.com/watch?v=YNiI6C0142g"
        ),
        Post(
            id = id++,
            likes = 15,
            shares = 20,
            views = 25,
            hasAutoLike = false,
            title = "Программирование",
            subTitle = "15 февраля",
            content = "Курсы по веб и мобильной разработке для новичков и junior-разработчиков. Вы освоите профессию разработчика с нуля или добавите в арсенал необходимый язык программирования.",
            video = "https://www.youtube.com/watch?v=i2dzkoHmFmc"
        ),
        Post(
            id = id++,
            likes = 16,
            shares = 995,
            views = 99,
            hasAutoLike = false,
            title = "Решитесь на большее",
            subTitle = "25 февраля",
            content = "Вам есть что показать этому миру. Позвольте себе ставить большие цели, а навыки и знания дадим мы. Для этого у нас есть все инструменты."
        ),
        Post(
            id = id++,
            likes = 16,
            shares = 35,
            views = 10,
            hasAutoLike = false,
            title = "Выберите вектор развития",
            subTitle = "25 февраля",
            content = "С нами вы можете освоить новую профессию, прокачаться в специальности или перенастроить свой бизнес. Выбирайте подходящую из более 80 программ."
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

    override fun removeById(id: Long) {
        posts = posts.filter {
            it.id != id
        }

        data.value = posts
    }

    override fun save(post: Post) {
        if(post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = id++,
                    title = "post title",
                    subTitle = "subtitle"
                )
            ) + posts

            data.value = posts
            return
        }

        posts = posts.map {
            if(it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
    }
}