package ru.netology

data class Post (
    val id: Long = 0L,
    var hasAutoLike: Boolean = false,
    var likes: Int = 0,
    var shares: Int = 0,
    var views: Int = 0
)