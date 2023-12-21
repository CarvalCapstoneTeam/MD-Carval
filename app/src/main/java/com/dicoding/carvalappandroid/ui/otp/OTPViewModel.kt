package com.dicoding.carvalappandroid.ui.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.carvalappandroid.data.JobRepository
import kotlinx.coroutines.launch

class OTPViewModel (private val repository: JobRepository) : ViewModel(){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun verification(email : String) = repository.verifyEmail(email)

    fun saveVerified(){
        viewModelScope.launch {
            repository.saveVerified()
        }
    }

    fun sendOTP(email: String, otp: String) = repository.sendOTP(email, otp)

}