package ru.netology.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.card_post.view.*
import ru.netology.R
import ru.netology.adapter.Listener
import ru.netology.adapter.PostsAdapter
import ru.netology.databinding.FragmentFeedBinding
import ru.netology.dto.Post
import ru.netology.viewmodel.PostViewModel

class FeedFragment : Fragment() {
    private lateinit var binding: FragmentFeedBinding
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

        //initViews(inflater.context.applicationContext)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews() {
        val adapter = PostsAdapter(object : Listener {
            override fun onShare(post: Post) {
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
                findNavController().navigate(R.id.action_feedFragment_to_postFragment)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onVideoView(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = post.link?.let { getUri(it) }
                }
                startActivity(intent)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, { posts ->
            adapter.submitList(posts)
        })

        binding.create.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_postFragment)
        }

        viewModel.edited.observe(viewLifecycleOwner, { post ->
            if (post.id == 0L) {
                return@observe
            }
        })
    }

    private fun getUri(link: String): Uri? {
        if (link.startsWith("http")) {
            return Uri.parse(link)
        }

        return Uri.parse("https://$link")
    }
}