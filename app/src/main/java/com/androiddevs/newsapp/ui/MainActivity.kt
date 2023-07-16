package com.androiddevs.newsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.androiddevs.newsapp.R
import com.androiddevs.newsapp.database.ArticleDatabase
import com.androiddevs.newsapp.repository.NewsRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ActivityMain"
    }

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
        super.onCreate(savedInstanceState)

            val repository = NewsRepository(ArticleDatabase(context = this))
            val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository = repository)
            viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

            setContentView(R.layout.activity_main)

            // set up navigation
            setUpNavigation()
        } catch (ex: Exception) {
            Log.i(TAG, "error ${ex.stackTraceToString()}")
        }
    }

    private fun setUpNavigation() {
        val nav: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        nav.setupWithNavController(navController)
    }

}
