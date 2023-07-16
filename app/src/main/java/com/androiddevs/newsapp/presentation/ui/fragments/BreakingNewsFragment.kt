package com.androiddevs.newsapp.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.ProgressBar
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
import com.androiddevs.newsapp.domain.util.Constants.Companion.COUNTRY_CODE
import com.androiddevs.newsapp.domain.util.Constants.Companion.QUERY_PAGE_SIZE
import com.androiddevs.newsapp.domain.util.Resource

class BreakingNewsFragment : Fragment (R.layout.fragment_breaking_news), NewsClickedListener {

    private companion object {
        const val TAG = "FragmentBreaking"
    }

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
                val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
                val shouldPagination = isNotLoadingAndNotLastPage && isAtLastPosition && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

                if (shouldPagination) {
                    viewModel.getBreakingNews(COUNTRY_CODE)
                    isScrolling = false
                }
            }
        })
    }

    private fun searchNews() {
        viewModel.breakingNews.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    progressBar.visibility = View.INVISIBLE
                    isLoading = false

                    it.data?.let { response ->
                        newsAdapter.differ.submitList(response.articles.toList())
                        val totalPages = response.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.breakingNewsPage == totalPages

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

        // Toast.makeText(context, "Article ${article.title}", Toast.LENGTH_SHORT).show()

        try {

            val bundle: Bundle = Bundle().apply {
                putSerializable("article", article)
            }

            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articlesFragment,
                bundle
            )

        } catch (ex: Exception) {
            Log.i(TAG, "error - ${ex.stackTraceToString()}")
        }
    }
}
