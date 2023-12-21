package com.dicoding.carvalappandroid.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.carvalappandroid.data.JobRepository
import com.dicoding.carvalappandroid.response.DetailResponse
import com.dicoding.carvalappandroid.utils.Result

class DetailViewModel (private val repository: JobRepository) : ViewModel(){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailStory(id : String) :LiveData<Result<DetailResponse>>{
        return repository.getDetailArticle(id)
    }

}