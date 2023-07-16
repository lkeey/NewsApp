package com.androiddevs.newsapp.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.newsapp.R
import com.androiddevs.newsapp.presentation.adapters.NewsAdapter
import com.androiddevs.newsapp.listeners.NewsClickedListener
import com.androiddevs.newsapp.models.Article
import com.androiddevs.newsapp.presentation.ui.activitites.MainActivity
import com.androiddevs.newsapp.presentation.viewModels.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class SavedNewsFragment : Fragment (R.layout.fragment_saved_news), NewsClickedListener {

    private companion object {
        const val TAG = "FragmentSaved"
    }

    private lateinit var savedArticles: RecyclerView
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        init(view)

        setUpAdapter()

        getArticles()
    }

    private fun init(view: View) {
        savedArticles = view.findViewById(R.id.rvSavedNews)
    }

    private fun setUpAdapter() {
        newsAdapter = NewsAdapter(this)

        savedArticles.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun getArticles() {

        // delete articles on swipe
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]

                viewModel.deleteArticle(article)
                Snackbar.make(requireView(), "Successfully delete", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        viewModel.addArticle(article)
                    }

                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(savedArticles)
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner) {
            newsAdapter.differ.submitList(it)
        }
    }

    override fun onNewsClicked(article: Article) {
        // Toast.makeText(context, "Saved article ${article.id}", Toast.LENGTH_SHORT).show()

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
