package com.androiddevs.newsapp.ui

import androidx.lifecycle.ViewModel
import com.androiddevs.newsapp.repository.NewsRepository

class NewsViewModel(
    val repository: NewsRepository
) : ViewModel() {


}
