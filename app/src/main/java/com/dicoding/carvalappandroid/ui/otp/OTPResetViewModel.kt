package com.dicoding.carvalappandroid.ui.otp

import androidx.lifecycle.ViewModel
import com.dicoding.carvalappandroid.data.JobRepository

class OTPResetViewModel(private val repository: JobRepository) : ViewModel() {

    fun sendOtpReset(email : String, otp : String) = repository.sendOTPReset(email, otp)

}