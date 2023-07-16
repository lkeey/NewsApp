package com.androiddevs.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.androiddevs.newsapp.R
import com.androiddevs.newsapp.ui.MainActivity
import com.androiddevs.newsapp.ui.NewsViewModel

class ArticlesFragment : Fragment (R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

}
