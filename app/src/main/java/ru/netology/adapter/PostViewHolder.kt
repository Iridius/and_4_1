package ru.netology.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.netology.R
import ru.netology.databinding.CardPostBinding
import ru.netology.dto.Post

class PostViewHolder (
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post : Post) {
        binding.apply {
            txtTitle.text = post.title
            txtSubtitle.text = post.subTitle
            txtContent.text = post.content

            imgLikes.setImageResource(
                if(post.hasAutoLike) R.drawable.ic_baseline_favorite_24
                else R.drawable.ic_baseline_favorite_border_24
            )

            imgLikes.setOnClickListener {
                onLikeListener(post)
            }
        }
    }
}