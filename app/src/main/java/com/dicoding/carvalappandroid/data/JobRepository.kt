package com.dicoding.carvalappandroid.data

import android.content.ContentValues.TAG
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
import com.dicoding.carvalappandroid.response.ChangePassResponse
import com.dicoding.carvalappandroid.response.ChangePasswordResponse
import com.dicoding.carvalappandroid.response.CheckTokenResponse
import com.dicoding.carvalappandroid.response.DataItem
import com.dicoding.carvalappandroid.response.DetailResponse
import com.dicoding.carvalappandroid.response.ForgotResponse
import com.dicoding.carvalappandroid.response.LoginResponse
import com.dicoding.carvalappandroid.response.MeResponse
import com.dicoding.carvalappandroid.response.ModelResponse
import com.dicoding.carvalappandroid.response.OTPForgotResponse
import com.dicoding.carvalappandroid.response.OTPResponse
import com.dicoding.carvalappandroid.response.RegisterResponse
import com.dicoding.carvalappandroid.response.UpdateProfileResponse
import com.dicoding.carvalappandroid.response.VerifiedResponse
import com.dicoding.carvalappandroid.setting.TokenPreference
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.UserModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class JobRepository(
    private val jobDatabase: JobDatabase,
    private val apiService: APIService,
    private val tokenPref: TokenPreference,
) {
    fun login(email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val success = apiService.login(email, password)
            emit(Result.Success(success))
        }
        catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(errorResponse.message?.let { Result.Error(it) })
        }
    }

    fun register(name: String, email: String, password: String, password2: String) = liveData {
        emit(Result.Loading)
        try {
            if (password == password2) {
                val success = apiService.register(name, email, password, password2)
                emit(Result.Success(success))
            } else {
                //setError
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }

    fun checkToken() : LiveData<Result<CheckTokenResponse>> = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.checkToken()

            emit(Result.Success(response))
        }catch (e : Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun sendResult(name : String, location : String, department : String, salaryRange : String, companyProfile : String, description : String, requirement : String
                   , benefits : String, telecommuting : Int) : LiveData<Result<ModelResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.sendResult(name, location,department,salaryRange,companyProfile,description,requirement,benefits,telecommuting)
            emit(Result.Success(response))
        }catch (e : Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun changePassword(password: String, newPassword: String, newPassword2:String) : LiveData<Result<ChangePasswordResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.changePassword(password, newPassword, newPassword2)
            emit(Result.Success(response))
        }catch (e:Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun updateProfile(name : String, email : String) : LiveData<Result<UpdateProfileResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.updateProfile(name, email)
            emit(Result.Success(response))
        }catch (e:Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun verifyEmail(email: String): LiveData<Result<OTPResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.verifyEmail(email)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun sendOTP(email: String, otp: String): LiveData<Result<VerifiedResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.sendOTP(email, otp)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
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

    fun getOTPReset(email: String): LiveData<Result<ForgotResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.sendOTPReset(email)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun sendOTPReset(email: String, otp : String): LiveData<Result<OTPForgotResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.resetCode(email, otp)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun resetPassword(email: String, newPassword: String, newPassword2:String): LiveData<Result<ChangePassResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.resetPass(email, newPassword, newPassword2)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getArticleUnlimited(searchQuery : String? = null): LiveData<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = JobRemoteMediator(jobDatabase, apiService, searchQuery),
            pagingSourceFactory = {
                jobDatabase.jobDAO().getAllArticle()
            }
        ).liveData
    }


    fun getDetailArticle(id: String): LiveData<Result<DetailResponse>> = liveData {
        emit(Result.Loading)
        try {

            Log.d(TAG, "API Request : id = $id")
            val response = apiService.getDetailArticle(id)
            Log.d(TAG, "Resp : $response")
            emit(Result.Success(response))

        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun saveSession(userModel: UserModel) {
        tokenPref.saveSession(userModel)
    }

    suspend fun saveDataUser(name : String, email : String) {
        tokenPref.saveDataUser(name, email)
    }

    suspend fun saveVerified() {
        tokenPref.saveVerified()
    }

    fun getSession(): Flow<UserModel> {
        return tokenPref.getSession()
    }

    suspend fun logout() {
        tokenPref.logout()
    }

}