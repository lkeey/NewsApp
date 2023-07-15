package com.androiddevs.newsapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.androiddevs.newsapp.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = REPLACE)
    suspend fun addArticle(article: Article)

    @Delete
    suspend fun deleteArticle()

    // unnecessary to use coroutines because it get live data
    @Query("SELECT * FROM news")
    fun getAllArticles(): LiveData<List<Article>>

}