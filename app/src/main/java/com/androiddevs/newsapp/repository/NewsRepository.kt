package com.androiddevs.newsapp.repository

import com.androiddevs.newsapp.api.RetrofitInstance
import com.androiddevs.newsapp.database.ArticleDatabase
import com.androiddevs.newsapp.models.Article
import com.androiddevs.newsapp.util.Constants.Companion.API_KEY
import retrofit2.Retrofit

class NewsRepository(
    private val database: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber, API_KEY)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNew(searchQuery, pageNumber, API_KEY)

    suspend fun addArticle(article: Article) =
        database.getArticlesDao().addArticle(article)

    suspend fun deleteArticle(article: Article) =
        database.getArticlesDao().deleteArticle(article)

    fun getSavedArticles() =
        database.getArticlesDao().getAllArticles()
}
