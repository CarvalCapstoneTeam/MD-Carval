package com.dicoding.carvalappandroid.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.carvalappandroid.data.JobRepository
import com.dicoding.carvalappandroid.utils.UserModel

class FormViewModel (private val repository: JobRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun sendResult(name : String, location : String, department : String, salaryRange : String, companyProfile : String, description : String, requirement : String
                   ,benefits : String, telecommuting : Int)
    = repository.sendResult(name, location,department,salaryRange,companyProfile,description,requirement,benefits,telecommuting)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

}