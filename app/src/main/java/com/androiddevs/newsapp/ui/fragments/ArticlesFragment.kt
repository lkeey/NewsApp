package com.androiddevs.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.androiddevs.newsapp.R
import com.androiddevs.newsapp.models.Article
import com.androiddevs.newsapp.ui.MainActivity
import com.androiddevs.newsapp.ui.NewsViewModel

class ArticlesFragment : Fragment (R.layout.fragment_article) {

    private lateinit var webView: WebView
    private lateinit var viewModel: NewsViewModel
    private val args: ArticlesFragmentArgs by navArgs()
    private lateinit var article: Article

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        init(view)

        receiveData()

        setWebView()
    }

    private fun init(view: View) {
        webView = view.findViewById(R.id.webView)
    }

    private fun receiveData() {
        article = args.article
    }

    private fun setWebView() {
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
    }

}
