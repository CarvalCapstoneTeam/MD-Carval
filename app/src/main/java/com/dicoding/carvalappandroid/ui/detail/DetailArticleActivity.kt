package com.dicoding.carvalappandroid.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html.fromHtml
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.ActivityDetailArticleBinding
import com.dicoding.carvalappandroid.response.DetailResponse
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.ViewModelFactory

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailArticleBinding
    private val viewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(this, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val slug = intent.getStringExtra("news_slug")

        binding.ibBack.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }


        viewModel.getDetailStory(slug.toString()).observe(this){result->
            when(result){
                is Result.Loading->{
                    Toast.makeText(this, slug, Toast.LENGTH_SHORT).show()
                }

                is Result.Success -> {
                    setData(result.data)
                }

                is Result.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }

    }

    private fun setData(data: DetailResponse) {
        Glide.with(this)
            .load(data.article?.thumbnail)
            .into(binding.ivThumbnail)
        binding.tvTitleDetail.text = data.article?.title
        binding.tvPenulis.text = data.article?.newsWriter
        binding.tvSourceDate.text = data.article?.source
        binding.tvNewsOutlet.text = data.article?.sourceDate
        binding.tvNews.text = data.article?.content
//        fromHtml(data.article?.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home->{
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}