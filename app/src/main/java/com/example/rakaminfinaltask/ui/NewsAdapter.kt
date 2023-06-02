package com.example.rakaminfinaltask.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rakaminfinaltask.data.local.entity.NewsEntity
import com.example.rakaminfinaltask.databinding.ItemNewsBinding
import com.example.rakaminfinaltask.utils.Helper

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    var newsList = ArrayList<NewsEntity>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            newsList.clear()
            newsList.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    class MyViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(news: NewsEntity) {
            Log.d(NewsAdapter::class.java.simpleName, "bind: ${news.title}")
            binding.tvNewsTitle.text = news.title
            binding.tvNewsDate.text = Helper.changeFormatDate(news.publishedAt)
            binding.tvNewsPublisher.text = news.publisher_name
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .into(binding.ivNewsImage)
            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(news.url)
                itemView.context.startActivity(intent)
            }
        }
    }
}