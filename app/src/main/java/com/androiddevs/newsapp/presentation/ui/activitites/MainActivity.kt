package com.androiddevs.newsapp.presentation.ui.activitites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.androiddevs.newsapp.R
import com.androiddevs.newsapp.data.database.ArticleDatabase
import com.androiddevs.newsapp.domain.repository.NewsRepository
import com.androiddevs.newsapp.presentation.viewModels.NewsViewModel
import com.androiddevs.newsapp.presentation.viewModels.NewsViewModelProviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getViewModel()

        setContentView(R.layout.activity_main)

        // set up navigation
        setUpNavigation()
    }

    private fun getViewModel() {
        val repository = NewsRepository(ArticleDatabase(context = this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository = repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]
    }

    private fun setUpNavigation() {
        val nav: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        nav.setupWithNavController(navController)
    }
}
