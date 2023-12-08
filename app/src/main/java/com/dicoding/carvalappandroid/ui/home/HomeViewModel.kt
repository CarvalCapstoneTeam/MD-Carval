package com.dicoding.carvalappandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.carvalappandroid.data.JobRepository
import com.dicoding.carvalappandroid.utils.UserModel

class HomeViewModel (private val repository: JobRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    val text: LiveData<String> = _text
}