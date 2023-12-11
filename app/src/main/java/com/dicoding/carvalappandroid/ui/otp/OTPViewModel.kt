package com.dicoding.carvalappandroid.ui.otp

import androidx.lifecycle.ViewModel
import com.dicoding.carvalappandroid.data.JobRepository

class OTPViewModel (private val repository: JobRepository) : ViewModel(){

    fun verification(email : String) = repository.verifyEmail(email)

}