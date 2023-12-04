package com.dicoding.carvalappandroid.data

import com.dicoding.carvalappandroid.api.APIService
import com.dicoding.carvalappandroid.setting.TokenPreference

class JobRepository constructor(
    private val jobDatabase: JobDatabase,
    private val apiService: APIService,
    private val tokenpref : TokenPreference
){
}