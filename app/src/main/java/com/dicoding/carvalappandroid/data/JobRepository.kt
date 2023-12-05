package com.dicoding.carvalappandroid.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.carvalappandroid.api.APIService
import com.dicoding.carvalappandroid.remotekeys.JobRemoteMediator
import com.dicoding.carvalappandroid.response.ArticleResponseItem
import com.dicoding.carvalappandroid.response.LoginResponse
import com.dicoding.carvalappandroid.response.RegisterResponse
import com.dicoding.carvalappandroid.setting.TokenPreference
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.UserModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class JobRepository constructor(
    private val jobDatabase: JobDatabase,
    private val apiService: APIService,
    private val tokenPref : TokenPreference
){

    fun login(email : String, password : String) = liveData{
        emit(Result.Loading)
        try {
            val success = apiService.login(email,password)
            emit(Result.Success(success))
        } catch (e : HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }

    fun register(name : String, email : String, password : String, password2 : String) = liveData{
        emit(Result.Loading)
        try {
            if (password == password2){
                val success = apiService.register(email, name, password)
                emit(Result.Success(success))
            }else{
                //setError
            }
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getArticle() : LiveData<PagingData<ArticleResponseItem>>{
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = JobRemoteMediator(jobDatabase, apiService),
            pagingSourceFactory = {
                jobDatabase.jobDAO().getAllArticle()
            }
        ).liveData
    }

    suspend fun saveSession(userModel: UserModel){
        tokenPref.saveSession(userModel)
    }

    fun getSession(): Flow<UserModel> {
        return tokenPref.getSession()
    }

    suspend fun logout(){
        tokenPref.logout()
    }

}