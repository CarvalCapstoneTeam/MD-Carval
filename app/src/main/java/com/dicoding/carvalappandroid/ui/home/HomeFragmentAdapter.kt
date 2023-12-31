package com.dicoding.carvalappandroid.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.carvalappandroid.databinding.ListHomeArticleBinding
import com.dicoding.carvalappandroid.response.HomeDataItem
import com.dicoding.carvalappandroid.ui.detail.DetailArticleActivity

class HomeFragmentAdapter : ListAdapter<HomeDataItem, HomeFragmentAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListHomeArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = getItem(position)
        if (list != null) {
            holder.bind(list)
        }
    }

    class ViewHolder(private val binding : ListHomeArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeDataItem){
            binding.tvTitle.text = item.title
            binding.tvTime.text = item.sourceDate
            binding.tvWriter.text = item.newsWriter
            Glide.with(binding.root)
                .load(item.thumbnail)
                .into(binding.ivUser)
            itemView.setOnClickListener{
                val intentToDetail = Intent(itemView.context, DetailArticleActivity::class.java)
                intentToDetail.putExtra("news_slug", item.slug)
                itemView.context.startActivity(intentToDetail)
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HomeDataItem>() {
            override fun areItemsTheSame(
                oldItem: HomeDataItem,
                newItem: HomeDataItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: HomeDataItem,
                newItem: HomeDataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}