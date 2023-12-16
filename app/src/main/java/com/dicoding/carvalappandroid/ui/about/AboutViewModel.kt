package com.dicoding.carvalappandroid.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.dicoding.carvalappandroid.data.JobRepository
import com.dicoding.carvalappandroid.response.UpdateProfileResponse
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.UserModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AboutViewModel(private val repository: JobRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun updateProfile(name : String, email : String) = repository.updateProfile(name, email)

    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun saveSession(userModel: UserModel){
        viewModelScope.launch {
            repository.saveSession(userModel)
        }
    }
}