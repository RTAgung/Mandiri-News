package com.example.rakaminfinaltask.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rakaminfinaltask.data.local.entity.HeadlinesNewsEntity
import com.example.rakaminfinaltask.databinding.ItemHeadlinesNewsBinding
import com.example.rakaminfinaltask.utils.Helper

class HeadlinesAdapter : RecyclerView.Adapter<HeadlinesAdapter.MyViewHolder>() {
    var headlinesList = ArrayList<HeadlinesNewsEntity>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            headlinesList.clear()
            headlinesList.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemHeadlinesNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = headlinesList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(headlinesList[position])
    }

    class MyViewHolder(private val binding: ItemHeadlinesNewsBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(news: HeadlinesNewsEntity) {
            Log.d(NewsAdapter::class.java.simpleName, "bind: ${news.title}")
            binding.tvHeadlinesTitle.text = news.title
            binding.tvHeadlinesDate.text = Helper.changeFormatDate(news.publishedAt)
            binding.tvHeadlinesPublisher.text = news.publisher_name
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .into(binding.ivHeadlinesImage)
            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(news.url)
                itemView.context.startActivity(intent)
            }
        }
    }
}