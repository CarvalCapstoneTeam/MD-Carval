package com.dicoding.carvalappandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.carvalappandroid.data.HomeRepository
import com.dicoding.carvalappandroid.response.HomeDataItem
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.UserModel
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: HomeRepository) : ViewModel() {

    private val _dataLoaded = MutableLiveData<Boolean>()
    val dataLoaded: LiveData<Boolean> get() = _dataLoaded

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getDataUser() = repository.getUserData()

    fun saveDataUser(name : String){
        viewModelScope.launch {
            repository.saveUsername(name)
        }
    }


    fun getArticle(): LiveData<Result<List<HomeDataItem>>> {
        return repository.getArticle()
    }

}