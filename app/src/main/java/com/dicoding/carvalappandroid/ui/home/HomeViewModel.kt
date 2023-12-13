package com.dicoding.carvalappandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.carvalappandroid.data.JobRepository
import com.dicoding.carvalappandroid.response.DataItem
import com.dicoding.carvalappandroid.utils.UserModel

class HomeViewModel (private val repository: JobRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    val getArticleUnlimited : LiveData<PagingData<DataItem>> =
        repository.getArticleUnlimited().cachedIn(viewModelScope)

    val text: LiveData<String> = _text
}