package com.dicoding.carvalappandroid.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.carvalappandroid.data.JobRepository
import com.dicoding.carvalappandroid.response.DataItem

class ArticleViewModel(private val repository: JobRepository) : ViewModel() {

    private val _searchQuery = MutableLiveData<String>().apply {
        value = null
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    val searchQuery: LiveData<String> get() = _searchQuery

    fun setSearchQuery(query: String) {
        if (_searchQuery.value != query) {
            _searchQuery.value = query
        }
    }

    val getArticleUnlimited : LiveData<PagingData<DataItem>> =
        _searchQuery.switchMap {
            repository.getArticleUnlimited(it).cachedIn(viewModelScope)
        }
}