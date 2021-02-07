package ru.netology

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val post = Post(id = 0, likes = 10, shares = 997, views = 5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* likes */
        val txtLike = findViewById<TextView>(R.id.txtLikes)
            txtLike.text = formatNumber(post.likes)

        findViewById<ImageButton>(R.id.imgLikes).setOnClickListener{
            val change = if(post.hasAutoLike) -1 else 1

            post.hasAutoLike = !post.hasAutoLike
            post.likes += change

            txtLike.text = formatNumber(post.likes)
        }

        /* shares */
        val txtShares = findViewById<TextView>(R.id.txtShares)
            txtShares.text = formatNumber(post.shares)

        findViewById<ImageButton>(R.id.imgShares).setOnClickListener{
            post.shares++
            txtShares.text = formatNumber(post.shares)
        }

        /* views */
        val txtViews = findViewById<TextView>(R.id.txtView)
            txtViews.text = formatNumber(post.views)

        findViewById<ImageButton>(R.id.imgViews).setOnClickListener{
            post.views++
            txtViews.text = formatNumber(post.views)
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