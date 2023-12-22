package com.dicoding.carvalappandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.carvalappandroid.data.JobRepository
import kotlinx.coroutines.launch

class MainViewModel (private val repository: JobRepository) : ViewModel() {

    fun checkToken() = repository.checkToken()

    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }

}