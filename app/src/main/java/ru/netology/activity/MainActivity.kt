package ru.netology.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.adapter.Listener
import ru.netology.adapter.PostsAdapter
import ru.netology.databinding.ActivityMainBinding
import ru.netology.dto.Post
import ru.netology.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews(binding)
    }

    private fun initViews(binding: ActivityMainBinding) {
        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : Listener {
            override fun onShare(post : Post) {
                viewModel.shareById(post.id)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onView(post: Post) {
                viewModel.viewById(post.id)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this, { posts ->
            adapter.submitList(posts)
        })
    }
}