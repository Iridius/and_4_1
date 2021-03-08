package ru.netology.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.netology.databinding.CardPostBinding
import ru.netology.dto.Post

interface Listener {
    fun onLike (post: Post)
    fun onShare (post: Post)
    fun onView (post : Post)
    fun onEdit(post: Post)
    fun onRemove (post: Post)
    fun onVideoView(post: Post)
}

class PostsAdapter (
    private val listener: Listener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PostViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}