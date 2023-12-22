package com.dicoding.carvalappandroid.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.carvalappandroid.data.JobRepository
import com.dicoding.carvalappandroid.utils.UserModel
import kotlinx.coroutines.launch

class AboutViewModel(private val repository: JobRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun updateProfile(name : String, email : String) = repository.updateProfile(name, email)

    fun getDataUser() = repository.getUserData()

    fun changePassword(password : String, newPassword : String, newPassword2: String) = repository.changePassword(password, newPassword, newPassword2)

    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun saveDataUser(name : String, email: String){
        viewModelScope.launch {
            repository.saveDataUser(name, email)
        }
    }
}