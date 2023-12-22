package com.dicoding.carvalappandroid.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.carvalappandroid.api.APIService
import com.dicoding.carvalappandroid.response.HomeDataItem
import com.dicoding.carvalappandroid.response.MeResponse
import com.dicoding.carvalappandroid.setting.TokenPreference
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.UserModel
import kotlinx.coroutines.flow.Flow

class HomeRepository(
    private val apiService: APIService,
    private val tokenPref: TokenPreference,
    private val dao: HomeDAO
) {

    fun getSession(): Flow<UserModel> {
        return tokenPref.getSession()
    }

    fun getUserData() : LiveData<Result<MeResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDataUser()
            emit(Result.Success(response))
        }catch (e:Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun saveUsername(name : String) {
        tokenPref.saveUsername(name)
    }

    fun getArticle() : LiveData<Result<List<HomeDataItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getArticles()
            dao.insertArticle(response.listArticle?.data ?: emptyList())
            response.listArticle?.data?.let { articles ->
                emit(Result.Success(articles))
            } ?: run {
                emit(Result.Error("Data is null"))
            }
        }catch (e:Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

}