package com.androiddevs.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.newsapp.R
import com.androiddevs.newsapp.adapters.NewsAdapter
import com.androiddevs.newsapp.listeners.NewsClickedListener
import com.androiddevs.newsapp.models.Article
import com.androiddevs.newsapp.ui.MainActivity
import com.androiddevs.newsapp.ui.NewsViewModel
import com.androiddevs.newsapp.util.Resource

class BreakingNewsFragment : Fragment (R.layout.fragment_breaking_news), NewsClickedListener {

    private companion object {
        const val TAG = "FragmentBreaking"
    }

    private lateinit var recyclerNews: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        init(view)

        setUpAdapter()

        searchNews()
    }

    private fun init(view: View) {
        recyclerNews = view.findViewById(R.id.rvBreakingNews)
        progressBar = view.findViewById(R.id.paginationProgressBar)
    }

    private fun setUpAdapter() {
        newsAdapter = NewsAdapter(this)

        recyclerNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun searchNews() {
        viewModel.breakingNews.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    progressBar.visibility = View.INVISIBLE

                    it.data?.let { response ->
                        newsAdapter.differ.submitList(response.articles)
                    }
                }

                is Resource.Error -> {
                    progressBar.visibility = View.INVISIBLE

                    it.message?.let { message ->
                        {
                            Log.i(TAG, "error - $message")
                        }
                    }
                }

                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onNewsClicked(article: Article) {
        Toast.makeText(context, "Article ${article.title}", Toast.LENGTH_SHORT).show()
    }
}
