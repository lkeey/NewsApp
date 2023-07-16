package com.androiddevs.newsapp.adapters.listeners

import com.androiddevs.newsapp.models.Article

interface NewsClickedListener {
    fun onNewsClicked(article: Article)
}