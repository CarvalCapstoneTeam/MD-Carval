package com.dicoding.carvalappandroid.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.asLiveData
import com.dicoding.carvalappandroid.api.APIService
import com.dicoding.carvalappandroid.response.HomeDataItem
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.setting.TokenPreference
import com.dicoding.carvalappandroid.utils.UserModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class HomeRepository constructor(
    private val homeDatabase: HomeDatabase,
    private val apiService: APIService,
    private val tokenPref: TokenPreference,
    private val dao: HomeDAO
) {

    fun getSession(): Flow<UserModel> {
        return tokenPref.getSession()
    }

    fun getData(): LiveData<List<HomeDataItem>>{
        return dao.getAllArticle()
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