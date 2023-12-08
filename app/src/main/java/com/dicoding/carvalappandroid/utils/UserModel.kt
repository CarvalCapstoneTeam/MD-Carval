package com.dicoding.carvalappandroid.utils

data class UserModel (
    val email : String,
    val username : String,
    val token : String,
    val isLogin : Boolean = false
)