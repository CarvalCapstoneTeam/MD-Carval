package com.dicoding.carvalappandroid

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.carvalappandroid.data.JobRepository
import com.dicoding.carvalappandroid.utils.UserModel
import kotlinx.coroutines.launch

class MainViewModel (private val repository: JobRepository) : ViewModel() {

    fun checkToken() = repository.checkToken()

    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }

}