package com.androiddevs.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.newsapp.R
import com.androiddevs.newsapp.listeners.NewsClickedListener
import com.androiddevs.newsapp.models.Article
import com.androiddevs.newsapp.ui.MainActivity
import com.androiddevs.newsapp.ui.NewsViewModel

class SavedNewsFragment : Fragment (R.layout.fragment_saved_news), NewsClickedListener {

    private lateinit var savedArticles: RecyclerView
    private lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        init(view)
    }

    private fun init(view: View) {
        savedArticles = view.findViewById(R.id.rvSavedNews)
    }

    override fun onNewsClicked(article: Article) {
        Toast.makeText(context, "Saved article ${article.title}", Toast.LENGTH_SHORT).show()

        val bundle: Bundle = Bundle().apply {
            putSerializable("article", article)
        }

        findNavController().navigate(
            R.id.action_searchNewsFragment_to_articlesFragment,
            bundle
        )
    }
}
