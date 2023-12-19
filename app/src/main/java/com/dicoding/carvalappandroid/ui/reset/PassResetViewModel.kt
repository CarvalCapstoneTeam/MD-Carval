package com.dicoding.carvalappandroid.ui.reset

import androidx.lifecycle.ViewModel
import com.dicoding.carvalappandroid.data.JobRepository

class PassResetViewModel(private val repository: JobRepository) : ViewModel() {

    fun resetPassword (email : String, newPassword : String, newPassword2 : String) = repository.resetPassword(email, newPassword, newPassword2)

}