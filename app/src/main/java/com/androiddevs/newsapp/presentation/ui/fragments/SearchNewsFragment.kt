package com.androiddevs.newsapp.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.newsapp.R
import com.androiddevs.newsapp.presentation.adapters.NewsAdapter
import com.androiddevs.newsapp.domain.listeners.NewsClickedListener
import com.androiddevs.newsapp.data.models.Article
import com.androiddevs.newsapp.presentation.ui.activitites.MainActivity
import com.androiddevs.newsapp.presentation.viewModels.NewsViewModel
import com.androiddevs.newsapp.domain.util.Constants
import com.androiddevs.newsapp.domain.util.Constants.Companion.TIME_DELAY
import com.androiddevs.newsapp.domain.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment (R.layout.fragment_search_news), NewsClickedListener {

    private companion object {
        const val TAG = "FragmentSearching"
    }

    private lateinit var searchingQuery: EditText
    private lateinit var recyclerNews: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        init(view)

        setListeners()

        setUpAdapter()

        searchNews()
    }

    private fun init(view: View) {
        recyclerNews = view.findViewById(R.id.rvSearchNews)
        progressBar = view.findViewById(R.id.paginationProgressBar)
        searchingQuery = view.findViewById(R.id.etSearch)
    }

    private fun setListeners() {

        var job: Job? = null

        searchingQuery.addTextChangedListener {
            job?.cancel()

            job = MainScope().launch {
                delay(TIME_DELAY)

                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchNews(it.toString())
                    }
                }
            }
        }
    }

    private fun setUpAdapter() {
        newsAdapter = NewsAdapter(this)

        recyclerNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        recyclerNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val manager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = manager.findFirstVisibleItemPosition()
                val visibleItemCount = manager.childCount
                val totalItemCount = manager.itemCount

                val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
                val isAtLastPosition = firstVisibleItemPosition + visibleItemCount >= totalItemCount
                val isNotAtBeginning = firstVisibleItemPosition >= 0
                val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
                val shouldPagination = isNotLoadingAndNotLastPage && isAtLastPosition && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

                if (shouldPagination) {
                    viewModel.searchNews(searchingQuery.text.toString())
                    isScrolling = false
                }
            }
        })

    }

    private fun searchNews() {
        viewModel.searchNews.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    progressBar.visibility = View.INVISIBLE
                    isLoading = false

                    it.data?.let { response ->
                        newsAdapter.differ.submitList(response.articles)
                        val totalPages = response.totalResults / Constants.QUERY_PAGE_SIZE + 2
                        isLastPage= viewModel.searchNewsPage == totalPages

                        if (isLastPage) {
                            recyclerNews.setPadding(0, 0, 0, 0 )
                        }
                    }
                }

                is Resource.Error -> {
                    progressBar.visibility = View.INVISIBLE
                    isLoading = false

                    it.message?.let { message ->
                        {
                            Log.i(TAG, "error - $message")
                        }
                    }
                }

                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    isLoading = true
                }
            }
        }
    }

    override fun onNewsClicked(article: Article) {
        // Toast.makeText(context, "Search article ${article.title}", Toast.LENGTH_SHORT).show()

        try {

            val bundle: Bundle = Bundle().apply {
                putSerializable("article", article)
            }

            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articlesFragment,
                bundle
            )

        } catch (ex: Exception) {
            Log.i(TAG, "error - ${ex.message}")
        }
    }
}
