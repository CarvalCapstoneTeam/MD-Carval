package com.dicoding.carvalappandroid.data

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
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
import com.dicoding.carvalappandroid.response.DetailResponse
import com.dicoding.carvalappandroid.response.LoginResponse
import com.dicoding.carvalappandroid.response.OTPResponse
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
            emit(errorResponse.message?.let { Result.Error(it) })
        }
    }

    fun register(name : String, email : String, password : String, password2 : String) = liveData{
        emit(Result.Loading)
        try {
            if (password == password2){
                val success = apiService.register(name, email, password, password2)
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

    fun verifyEmail (email : String) : LiveData<Result<OTPResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.verifyEmail(email)
            emit(Result.Success(response))
        }catch (e:Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getArticleUnlimited() : LiveData<PagingData<ArticleResponseItem>>{
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


    fun getArticle() : LiveData<Result<List<ArticleResponseItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getArticles()
            emit(Result.Success(response.listArticle))
        }catch (e:Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDetailArticle(id : String) : LiveData<Result<DetailResponse>> = liveData {
        emit(Result.Loading)
        try {
            Log.d(TAG, "API Request : id = $id")
            val response = apiService.getDetailArticle(id)

            if (response != null){
                Log.d(TAG, "Resp : $response")
                emit(Result.Success(response))
            }else{
                val errorMessage = "Empty Response"
                Log.e(TAG, errorMessage)
                emit(Result.Error(errorMessage))
            }
        }catch (e:Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun saveSession(userModel: UserModel){
        tokenPref.saveSession(userModel)
    }

    suspend fun saveVerified(userModel: UserModel){
        tokenPref.saveVerified(userModel)
    }

    fun getSession(): Flow<UserModel> {
        return tokenPref.getSession()
    }

    suspend fun logout(){
        tokenPref.logout()
    }

}