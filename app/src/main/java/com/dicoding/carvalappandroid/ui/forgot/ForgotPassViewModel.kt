package com.dicoding.carvalappandroid.ui.forgot

import androidx.lifecycle.ViewModel
import com.dicoding.carvalappandroid.data.JobRepository

class ForgotPassViewModel(private val repository: JobRepository) : ViewModel() {

    fun getOTPReset(email: String) = repository.getOTPReset(email)

}