package com.dicoding.carvalappandroid.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.carvalappandroid.data.JobRepository
import com.dicoding.carvalappandroid.utils.UserModel

class SplashScreenViewModel(private val repository: JobRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

}