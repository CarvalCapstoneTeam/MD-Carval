package com.dicoding.carvalappandroid.ui.article

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.carvalappandroid.databinding.ListItemArticleBinding
import com.dicoding.carvalappandroid.response.ArticleResponseItem
import com.dicoding.carvalappandroid.ui.detail.DetailArticleActivity

class ArticleAdapter :
    ListAdapter<ArticleResponseItem, ArticleAdapter.ViewHolder>(DIFF_CALLBACK) {
//    PagingDataAdapter<ArticleResponseItem, ArticleAdapter.ViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = getItem(position)
        if (list != null) {
            holder.bind(list)
        }
    }

    class ViewHolder(private val binding : ListItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticleResponseItem){
            binding.tvTitle.text = item.title
            binding.tvTime.text = item.sourceDate
            binding.tvWriter.text = item.source
            Glide.with(binding.root)
                .load(item.thumbnail)
                .into(binding.ivNews)
            itemView.setOnClickListener{
                val intentToDetail = Intent(itemView.context, DetailArticleActivity::class.java)
                intentToDetail.putExtra("news_slug", item.slug)
                itemView.context.startActivity(intentToDetail)
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleResponseItem>() {
            override fun areItemsTheSame(
                oldItem: ArticleResponseItem,
                newItem: ArticleResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ArticleResponseItem,
                newItem: ArticleResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}