package com.dicoding.carvalappandroid.api

import com.dicoding.carvalappandroid.response.ArticleResponse
import com.dicoding.carvalappandroid.response.ChangePassResponse
import com.dicoding.carvalappandroid.response.ChangePasswordResponse
import com.dicoding.carvalappandroid.response.CheckTokenResponse
import com.dicoding.carvalappandroid.response.DetailResponse
import com.dicoding.carvalappandroid.response.ForgotResponse
import com.dicoding.carvalappandroid.response.HomeArticleResponse
import com.dicoding.carvalappandroid.response.LoginResponse
import com.dicoding.carvalappandroid.response.MeResponse
import com.dicoding.carvalappandroid.response.ModelResponse
import com.dicoding.carvalappandroid.response.OTPForgotResponse
import com.dicoding.carvalappandroid.response.OTPResponse
import com.dicoding.carvalappandroid.response.RegisterResponse
import com.dicoding.carvalappandroid.response.UpdateProfileResponse
import com.dicoding.carvalappandroid.response.VerifiedResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirm: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("email-verification")
    suspend fun verifyEmail(
        @Field("email") email: String,
    ): OTPResponse

    @FormUrlEncoded
    @POST("verify-email")
    suspend fun sendOTP(
        @Field("email") email: String,
        @Field("otp") otp: String
    ): VerifiedResponse

    @FormUrlEncoded
    @POST("predict")
    suspend fun sendResult(
        @Field("title") jobName: String,
        @Field("location") location : String,
        @Field("department") department: String,
        @Field("salary_range") salaryRange: String,
        @Field("company_profile") profile: String,
        @Field("description") description: String,
        @Field("requirement") requirement: String,
        @Field("benefits") benefits: String,
        @Field("telecommuting") telecommuting: Int
    ) : ModelResponse

    @GET("articles")
    suspend fun getArticles(
        @Query("keyword") keyword: String? = null,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 5
    ): HomeArticleResponse

    @GET("me")
    suspend fun getDataUser() : MeResponse

    @FormUrlEncoded
    @POST("forgot-password")
    suspend fun sendOTPReset(
        @Field("email") email: String
    ): ForgotResponse

    @FormUrlEncoded
    @POST("verify-otp")
    suspend fun resetCode(
        @Field("email") email: String,
        @Field("otp") otp : String
    ) : OTPForgotResponse

    @FormUrlEncoded
    @POST("reset-password")
    suspend fun resetPass(
        @Field("email") email: String,
        @Field("new_password") newPassword: String,
        @Field("new_password_confirmation") newPasswordConfirmation: String,
    ) : ChangePassResponse

    @POST("check-token")
    suspend fun checkToken(): CheckTokenResponse

    @FormUrlEncoded
    @PUT("update-profile")
    suspend fun updateProfile(
        @Field("name") name: String,
        @Field("email") email: String
    ): UpdateProfileResponse

    @FormUrlEncoded
    @PUT("change-password")
    suspend fun changePassword(
        @Field("current_password") currentPassword: String,
        @Field("new_password") newPassword: String,
        @Field("new_password_confirmation") newPasswordConfirmation: String,
    ): ChangePasswordResponse

    @GET("articles")
    suspend fun getArticlesUnlimited(
        @Query("keyword") keyword: String? = null,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ArticleResponse

//    @Query("keyword") keyword : String?,

    @GET("articles/{slug}/show")
    suspend fun getDetailArticle(@Path("slug") slug: String): DetailResponse

}