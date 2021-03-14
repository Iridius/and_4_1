package ru.netology.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.databinding.FragmentPostBinding
import ru.netology.viewmodel.PostViewModel

class PostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels (
        ownerProducer = ::requireParentFragment
    )

    companion object {
        private const val TEXT_KEY = "TEXT_KEY"
        var Bundle.textArg: String?
            get() = getString(TEXT_KEY)
            set(value) = putString(TEXT_KEY, value)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPostBinding.inflate(
            inflater,
            container,
            false
        )

        arguments?.textArg?.let(binding.content::setText)

        with(binding) {
            /* content */
            content.requestFocus()
            content.setText(viewModel.edited.value?.content)

            /* link */
            link.setText(viewModel.edited.value?.link)

            /* fab: ok */
            save.setOnClickListener {
                viewModel.changeContent(content.text.toString(), link.text.toString())
                viewModel.save()

                findNavController().navigateUp()
            }
        }

        return binding.root
    }
}
