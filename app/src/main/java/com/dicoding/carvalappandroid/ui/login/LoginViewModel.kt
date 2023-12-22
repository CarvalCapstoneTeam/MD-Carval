package com.dicoding.carvalappandroid.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.carvalappandroid.data.JobRepository
import com.dicoding.carvalappandroid.utils.UserModel
import kotlinx.coroutines.launch


class LoginViewModel(private val repository: JobRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email : String, password : String) = repository.login(email, password)

    fun saveSession(userModel: UserModel){
        viewModelScope.launch {
            repository.saveSession(userModel)
        }
    }

    fun saveVerified(){
        viewModelScope.launch {
            repository.saveVerified()
        }
    }


}