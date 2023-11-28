package com.dicoding.carvalappandroid.api

import com.dicoding.carvalappandroid.response.ArticleResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {
//    @FormUrlEncoded
//    @POST("login")
//    suspend fun login(
//        @Field("username") username : String
//        @Field("password") password : String
//    ) : LoginResponse

//    @FormUrlEncoded
//    @POST("register")
//    suspend fun register(
//        @Field("email") email : String
//        @Field("username") username : String
//        @Field("password") password : String
//    ) : LoginResponse

    @GET("news")
    suspend fun getArticles() : ArticleResponse

//    @GET("news/{id}")
//    suspend fun getDetailArticle(@Path("id") id : String) : DetailResponse
}