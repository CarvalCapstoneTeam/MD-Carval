package com.dicoding.carvalappandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.carvalappandroid.data.HomeRepository
import com.dicoding.carvalappandroid.data.JobRepository
import com.dicoding.carvalappandroid.response.DataItem
import com.dicoding.carvalappandroid.response.HomeDataItem
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.UserModel

class HomeViewModel (private val repository: HomeRepository) : ViewModel() {

    private val _dataLoaded = MutableLiveData<Boolean>()
    val dataLoaded: LiveData<Boolean> get() = _dataLoaded

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getArticle(): LiveData<Result<List<HomeDataItem>>> {
        return repository.getArticle()
    }

    fun getData(): LiveData<List<HomeDataItem>>{
        return repository.getData()
    }
}