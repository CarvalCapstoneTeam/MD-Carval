package com.dicoding.carvalappandroid.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.carvalappandroid.data.JobRepository

class FormViewModel (private val repository: JobRepository): ViewModel() {

    fun sendResult(name : String, location : String, department : String, salaryRange : String, companyProfile : String, description : String, requirement : String
                   ,benefits : String, telecommuting : Int)
    = repository.sendResult(name, location,department,salaryRange,companyProfile,description,requirement,benefits,telecommuting)

}