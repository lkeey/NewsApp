package com.androiddevs.newsapp.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.androiddevs.newsapp.R
import com.androiddevs.newsapp.data.models.Article
import com.androiddevs.newsapp.presentation.ui.activitites.MainActivity
import com.androiddevs.newsapp.presentation.viewModels.NewsViewModel
import com.androiddevs.newsapp.ui.fragments.ArticlesFragmentArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ArticlesFragment : Fragment (R.layout.fragment_article) {

    private lateinit var webView: WebView
    private lateinit var fab: FloatingActionButton
    private lateinit var viewModel: NewsViewModel
    private val args: ArticlesFragmentArgs by navArgs()
    private lateinit var article: Article

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        init()

        setListeners()

        receiveData()

        setWebView()
    }

    private fun init() {

        webView = requireView().findViewById(R.id.webView)
        fab = requireView().findViewById(R.id.fab)
    }

    private fun receiveData() {
        article = args.article
    }

    private fun setListeners() {
        fab.setOnClickListener {
            viewModel.addArticle(article)

            Snackbar.make(requireView(), "Article Saved", Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun setWebView() {
        webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }
    }
}
