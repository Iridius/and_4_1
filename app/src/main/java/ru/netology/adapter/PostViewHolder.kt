package ru.netology.adapter

import android.content.Intent
import android.widget.PopupMenu
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import ru.netology.R
import ru.netology.activity.PostActivity
import ru.netology.databinding.CardPostBinding
import ru.netology.dto.Post

class PostViewHolder (
    private val binding: CardPostBinding,
    private val listener: Listener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post : Post) {
        binding.apply {
            txtTitle.text = post.title
            txtSubtitle.text = post.subTitle
            txtContent.text = post.content

            /* link */
            link.text = post.video
            link.setOnClickListener {
                listener.onVideoView(post)
            }

            /* likes */
            likes.text = formatNumber(post.likes)
            likes.isChecked = post.hasAutoLike
            likes.setOnClickListener {
                listener.onLike(post)
            }

            /* shares */
            shares.text = formatNumber(post.shares)
            shares.setOnClickListener {
                listener.onShare(post)
            }

            /* views */
            views.text = formatNumber(post.views)
            views.setOnClickListener {
                listener.onView(post)
            }

            /* card menu */
            imageMenu.setOnClickListener{
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    this.setOnMenuItemClickListener { item->
                        when (item.itemId) {
                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                            }
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}

fun formatNumber(number: Int): String {
    return when {
        number >= 1_000_000 -> roundNumber(number / 1_000_000.0) + "M"
        number >= 1_000 -> roundNumber(number / 1_000.0) + "K"
        else -> number.toString()
    }
}

fun roundNumber(number: Double): String {
    val value = number.toString().take(3)

    return when {
        value.endsWith(".") -> value.take(2)
        value.endsWith(".0") -> value.take(1)
        else -> value
    }
}