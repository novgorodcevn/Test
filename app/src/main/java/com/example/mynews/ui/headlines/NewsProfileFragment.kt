package com.example.mynews.ui.headlines

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.mynews.R
import com.example.mynews.data.Article
import com.example.mynews.databinding.FragmentNewsProfileBinding
import com.example.mynews.viewmodel.NewsProfileUiState
import com.example.mynews.viewmodel.NewsProfileViewModel
import kotlinx.coroutines.launch


class NewsProfileFragment : Fragment() {

    private var _binding: FragmentNewsProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsProfileViewModel by viewModels()
    private var newsUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_ARTICLE, Article::class.java)
        } else {
            arguments?.getParcelable<Article>(ARG_ARTICLE)
        }

        if (article != null) {
            newsUrl = article.url

            // Observe the state from ViewModel
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collect { state ->
                        when (state) {
                            is NewsProfileUiState.Loading -> {
                                // Show loading indicator
                                Log.d("NewsProfileFragment", "Loading")
                            }
                            is NewsProfileUiState.Success -> {
                                // Update UI with the article details
                                Log.d("NewsProfileFragment", "Success")
                                binding.apply {
                                    newsTitleTextView.text = article.title
                                    newsDescriptionTextView.text = article.description
                                    newsSourceTextView.text = article.source.name

                                    Glide.with(requireContext())
                                        .load(article.urlToImage)
                                        .placeholder(R.drawable.ic_launcher_background)
                                        .error(R.drawable.ic_launcher_background)
                                        .into(newsImageView)

                                    val fullDescription = article.description ?: ""
                                    val lastSentence = fullDescription.substringAfterLast(". ")

                                    val spannableString = SpannableString(lastSentence)
                                    val clickableSpan = object : ClickableSpan() {
                                        override fun onClick(widget: View) {
                                            // Open the URL in browser
                                            openNewsInBrowser()
                                        }
                                    }
                                    spannableString.setSpan(clickableSpan, 0, lastSentence.length, 0)
                                    clickableTextView.text = spannableString
                                    clickableTextView.movementMethod = android.text.method.LinkMovementMethod.getInstance()

                                    // Set favorite icon based on state
                                    val icon = if (state.isFavorite) {
                                        R.drawable.ic_saved
                                    } else {
                                        R.drawable.ic_sources
                                    }
                                    favoriteButton.setImageDrawable(
                                        ContextCompat.getDrawable(requireContext(), icon)
                                    )
                                    favoriteButton.setOnClickListener {
                                        viewModel.toggleFavorite()
                                    }
                                }
                            }
                            is NewsProfileUiState.Error -> {
                                // Show error message
                                Log.e("NewsProfileFragment", "Error: ${state.message}")
                                Toast.makeText(context, "Error: ${state.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }

            // Load data from ViewModel
            viewModel.loadNews(article)

        } else {
            Log.e("NewsProfileFragment", "Article is null")
            Toast.makeText(context, "Article is null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openNewsInBrowser() {
        newsUrl?.let { url ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } ?: run {
            Toast.makeText(context, "News URL is null", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_ARTICLE = "article"

        fun newInstance(article: Article): NewsProfileFragment {
            val fragment = NewsProfileFragment()
            val args = Bundle()
            args.putParcelable(ARG_ARTICLE, article)
            fragment.arguments = args
            return fragment
        }
    }
}
