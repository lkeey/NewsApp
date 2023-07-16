package com.androiddevs.newsapp.domain.listeners

import com.androiddevs.newsapp.data.models.Article

interface NewsClickedListener {
    fun onNewsClicked(article: Article)
}
