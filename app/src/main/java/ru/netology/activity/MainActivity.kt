gitpackage ru.netology.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.card_post.view.*
import ru.netology.adapter.Listener
import ru.netology.adapter.PostsAdapter
import ru.netology.databinding.ActivityMainBinding
import ru.netology.dto.Global
import ru.netology.dto.Post
import ru.netology.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    private val POST_ADD: Int = 100
    private val POST_EDIT: Int = 110
    private val viewModel: PostViewModel by viewModels()

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

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onVideoView(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(post.link)
                }
                startActivity(intent)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this, { posts ->
            adapter.submitList(posts)
        })

        binding.create.setOnClickListener {
            val intent = Intent(this@MainActivity, PostActivity::class.java)
            startActivityForResult(intent, POST_ADD)
        }

        viewModel.edited.observe(this, {post ->
            if (post.id == 0L) {
                return@observe
            }

            val intent = Intent(this@MainActivity, PostActivity::class.java)
            intent.putExtra(Global.CONTENT, post.content)
            intent.putExtra(Global.LINK, post.link)
            startActivityForResult(intent, POST_EDIT)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            POST_ADD, POST_EDIT -> {
                if(resultCode != Activity.RESULT_OK) {
                    return
                }

                val content = data?.getStringExtra(Global.CONTENT)
                val link = data?.getStringExtra(Global.LINK)

                viewModel.changeContent(content, link)
                viewModel.save()
            }
        }
    }
}