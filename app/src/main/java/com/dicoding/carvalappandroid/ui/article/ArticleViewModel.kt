package com.dicoding.carvalappandroid.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.carvalappandroid.data.JobRepository
import com.dicoding.carvalappandroid.response.ArticleResponseItem
import com.dicoding.carvalappandroid.utils.Result

class ArticleViewModel(private val repository: JobRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is article Fragment"
    }
    val text: LiveData<String> = _text

    fun getArticles(): LiveData<Result<List<ArticleResponseItem>>>{
        return repository.getArticle()
    }
}