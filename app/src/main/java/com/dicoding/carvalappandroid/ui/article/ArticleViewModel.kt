package com.dicoding.carvalappandroid.ui.article

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.carvalappandroid.data.JobRepository
import com.dicoding.carvalappandroid.response.DataItem
import com.dicoding.carvalappandroid.utils.Result
import kotlinx.coroutines.launch

class ArticleViewModel(private val repository: JobRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is article Fragment"
    }
    val text: LiveData<String> = _text


    val getArticleUnlimited : LiveData<PagingData<DataItem>> =
        repository.getArticleUnlimited().cachedIn(viewModelScope)
}