package ru.netology

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews(binding)
    }

    private fun initViews(binding: ActivityMainBinding) {
        with(binding) {
            /* ViewModel */
            val viewModel: PostViewModel by viewModels()
            viewModel.data.observe(this@MainActivity, { post ->
                imgLikes.setImageResource(
                    if (post.hasAutoLike) R.drawable.ic_baseline_favorite_24
                    else R.drawable.ic_baseline_favorite_border_24
                )

                txtLikes.text = formatNumber(post.likes)
                txtShares.text = formatNumber(post.shares)
                txtViews.text = formatNumber(post.views)

            })

            imgLikes.setOnClickListener {
                viewModel.like()
            }

            imgShares.setOnClickListener {
                viewModel.share()
            }

            imgViews.setOnClickListener {
                viewModel.view()
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