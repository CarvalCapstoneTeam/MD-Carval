package com.dicoding.carvalappandroid.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.carvalappandroid.data.JobRepository

class FormViewModel (private val repository: JobRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Form Fragment"
    }
    val text: LiveData<String> = _text
}