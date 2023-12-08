package com.dicoding.carvalappandroid.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.carvalappandroid.data.JobRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AboutViewModel(private val repository: JobRepository) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is about Fragment"
    }
    val text: LiveData<String> = _text

    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }
}