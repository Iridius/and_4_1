package ru.netology.adapter

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.R
import ru.netology.databinding.CardPostBinding
import ru.netology.dto.Global
import ru.netology.dto.Post

class PostViewHolder (
    private val binding: CardPostBinding,
    private val listener: Listener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post : Post) {
        binding.apply {
            root.setOnClickListener {
                val i = 0;
            }

            txtTitle.text = post.title
            txtSubtitle.text = post.subTitle
            txtContent.text = post.content

            /* link */
            link.text = post.link
            link.setOnClickListener {
                listener.onVideoView(post)
            }

            /* likes */
            likes.text = Global.formatNumber(post.likes)
            likes.isChecked = post.hasAutoLike
            likes.setOnClickListener {
                listener.onLike(post)
            }

            /* shares */
            shares.text = Global.formatNumber(post.shares)
            shares.setOnClickListener {
                listener.onShare(post)
            }

            /* views */
            views.text = Global.formatNumber(post.views)
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