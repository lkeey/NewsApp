package com.androiddevs.newsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "news"
)
class Article (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val author: String,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
) : Serializable
