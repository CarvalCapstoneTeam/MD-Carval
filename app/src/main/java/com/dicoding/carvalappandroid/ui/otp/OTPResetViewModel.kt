package com.dicoding.carvalappandroid.ui.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.carvalappandroid.data.JobRepository

class OTPResetViewModel(private val repository: JobRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun sendOtpReset(email : String, otp : String) = repository.sendOTPReset(email, otp)

    fun getOTPReset(email: String) = repository.getOTPReset(email)

}