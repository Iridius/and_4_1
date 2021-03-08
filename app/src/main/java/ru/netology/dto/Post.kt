package ru.netology.dto

data class Post (
    val id: Long = 0L,
    val hasAutoLike: Boolean = false,
    val likes: Int = 0,
    val shares: Int = 0,
    val views: Int = 0,
    val title: String = "",
    val subTitle: String = "",
    val content: String = "",
    val video: String? = ""
)