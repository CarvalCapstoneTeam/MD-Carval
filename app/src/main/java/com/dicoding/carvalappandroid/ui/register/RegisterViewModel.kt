package com.dicoding.carvalappandroid.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.carvalappandroid.data.JobRepository

class RegisterViewModel (private val repository: JobRepository) : ViewModel(){


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(name : String, email : String, password : String, password2 : String) =
        repository.register(name, email,  password, password2)

}