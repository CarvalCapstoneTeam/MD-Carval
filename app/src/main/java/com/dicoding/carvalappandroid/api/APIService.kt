package com.dicoding.carvalappandroid.api

import com.dicoding.carvalappandroid.response.ArticleResponse
import com.dicoding.carvalappandroid.response.DetailResponse
import com.dicoding.carvalappandroid.response.LoginResponse
import com.dicoding.carvalappandroid.response.OTPResponse
import com.dicoding.carvalappandroid.response.RegisterResponse
import com.dicoding.carvalappandroid.response.ResultResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email : String,
        @Field("password") password : String
    ) : LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("password_confirmation") passwordConfirm : String
    ) : RegisterResponse

    @FormUrlEncoded
    @POST("email-verification")
    suspend fun verifyEmail(
        @Field("email") email : String,
    ) : OTPResponse

    @GET("articles")
    suspend fun getArticles() : ArticleResponse

    @GET("articles")
    suspend fun getArticlesUnlimited(
        @Query("page") page : Int = 1,
        @Query("size") size : Int = 20
    ) : ArticleResponse

//    @Query("keyword") keyword : String?,

    @GET("articles/{slug}/show")
    suspend fun getDetailArticle(@Path("slug") slug : String) : DetailResponse

    @GET("result/{num}")
    suspend fun getResultById(@Path("num") num : Int) : ResultResponse
}