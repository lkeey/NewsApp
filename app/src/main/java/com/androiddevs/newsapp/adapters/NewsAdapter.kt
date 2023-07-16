package com.androiddevs.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.newsapp.R
import com.androiddevs.newsapp.listeners.NewsClickedListener
import com.androiddevs.newsapp.models.Article
import com.bumptech.glide.Glide

class NewsAdapter (
    val listener: NewsClickedListener
) : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder> () {

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article: Article = differ.currentList[position]

        holder.setData(article)

    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val img: ImageView = itemView.findViewById(R.id.ivArticleImage)
        private val source: TextView = itemView.findViewById(R.id.tvSource)
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val description: TextView = itemView.findViewById(R.id.tvDescription)
        private val published: TextView = itemView.findViewById(R.id.tvPublishedAt)

        fun setData(article: Article) {

            Glide.with(itemView.context).load(article.urlToImage).into(img)

            source.text = article.source.name
            title.text = article.title
            description.text = article.description
            published.text = article.publishedAt

            itemView.setOnClickListener { listener.onNewsClicked(article) }
        }

    }

}
