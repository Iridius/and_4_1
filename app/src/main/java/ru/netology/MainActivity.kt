package ru.netology

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import org.jetbrains.annotations.NotNull
import ru.netology.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val post = Post(id = 0, likes = 10, shares = 997, views = 5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews(binding)
    }

    private fun initViews(binding: ActivityMainBinding) {
        /* likes */
        binding.txtLikes.text = formatNumber(post.likes)
        binding.imgLikes.setOnClickListener {
            if(!post.hasAutoLike) {
                post.likes++
                binding.imgLikes.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                post.likes--
                binding.imgLikes.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }

            post.hasAutoLike = !post.hasAutoLike

            binding.txtLikes.text = formatNumber(post.likes)
        }

        /* shares */
        binding.txtShares.text = formatNumber(post.shares)
        binding.imgShares.setOnClickListener {
            post.shares++
            binding.txtShares.text = formatNumber(post.shares)
        }

        /* views */
        binding.txtViews.text = formatNumber(post.views)
        binding.imgViews.setOnClickListener {
            post.views++
            binding.txtViews.text = formatNumber(post.views)
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