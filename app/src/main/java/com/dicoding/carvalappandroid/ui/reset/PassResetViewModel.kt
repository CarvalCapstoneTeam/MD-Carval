package com.dicoding.carvalappandroid.ui.reset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.carvalappandroid.data.JobRepository

class PassResetViewModel(private val repository: JobRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun resetPassword (email : String, newPassword : String, newPassword2 : String) = repository.resetPassword(email, newPassword, newPassword2)

}