package ru.netology.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import kotlinx.android.synthetic.main.card_post.view.*
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

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this, { posts ->
            adapter.submitList(posts)
        })

        viewModel.edited.observe(this, {post ->
            //binding.group.visibility = View.VISIBLE

            if (post.id == 0L) {
                return@observe
            }

            with(binding.txtContent) {
                requestFocus()
                setText(post.content)
            }
        })

        /* change text */
        binding.txtContent.doAfterTextChanged {
            if(viewModel.edited.value?.content != it.toString()) {
                binding.btnCancel.visibility = View.VISIBLE
            } else {
                binding.btnCancel.visibility = View.INVISIBLE
            }
        }

        /* save */
        binding.btnSave.setOnClickListener {
            with(binding.txtContent) {
                if(text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Content can't be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
                //binding.group.visibility = View.GONE
            }
        }

        /* cancel */
        binding.btnCancel.setOnClickListener {
            with(binding.txtContent) {
                setText(viewModel.edited.value?.content)
            }
        }
    }
}

object AndroidUtils {
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}