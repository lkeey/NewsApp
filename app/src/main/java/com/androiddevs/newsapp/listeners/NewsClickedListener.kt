package com.androiddevs.newsapp.listeners

import com.androiddevs.newsapp.models.Article

interface NewsClickedListener {
    fun onNewsClicked(article: Article)
}
