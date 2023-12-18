package com.dicoding.carvalappandroid.utils

import android.content.Context
import com.dicoding.carvalappandroid.api.APIConfig
import com.dicoding.carvalappandroid.api.APIService
import com.dicoding.carvalappandroid.data.HomeDatabase
import com.dicoding.carvalappandroid.data.HomeRepository
import com.dicoding.carvalappandroid.data.JobDatabase
import com.dicoding.carvalappandroid.data.JobRepository
import com.dicoding.carvalappandroid.setting.TokenPreference
import com.dicoding.carvalappandroid.setting.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {

    fun provideRepository(context: Context) : JobRepository {
        val pref = TokenPreference.getInstance(context.dataStore)
        val apiService = provideAPIService(context)
        val database = JobDatabase.getDatabase(context)
        return JobRepository(database, apiService, pref)
    }

    fun provideRepository2(context: Context) : HomeRepository {
        val pref = TokenPreference.getInstance(context.dataStore)
        val apiService = provideAPIService(context)
        val database = HomeDatabase.getHomeDatabase(context)
        val dao = database.homeDao()
        return HomeRepository(database, apiService, pref, dao)
    }

    private fun provideAPIService(context: Context): APIService {
        val pref = TokenPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        return APIConfig.getAPIService(user.token)
    }
}