package ru.netology.adapter

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.R
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

            /* likes */
            likes.text = formatNumber(post.likes)
            likes.setIconResource(
                if(post.hasAutoLike) R.drawable.ic_baseline_favorite_24
                else R.drawable.ic_baseline_favorite_border_24
            )
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
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(post)
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