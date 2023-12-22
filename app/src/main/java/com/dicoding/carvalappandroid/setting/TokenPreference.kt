package com.dicoding.carvalappandroid.setting

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.carvalappandroid.utils.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "Token")


class TokenPreference private constructor(private val dataStore : DataStore<Preferences>){

    suspend fun saveSession(user: UserModel){
        dataStore.edit { preferences->
            preferences[EMAIL_KEY] = user.email
            preferences[USERNAME_KEY] = user.username
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = true
        }
    }

    suspend fun saveDataUser(username : String, email : String ){
        dataStore.edit { preferences->
            preferences[USERNAME_KEY] = username
            preferences[EMAIL_KEY] = email
        }
    }

    suspend fun saveUsername(username : String){
        dataStore.edit { preferences->
            preferences[USERNAME_KEY] = username
        }
    }

    suspend fun saveVerified(){
        dataStore.edit { preferences ->
            preferences[IS_VERIFIED_KEY] =  true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[EMAIL_KEY] ?: "",
                preferences[USERNAME_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false,
                preferences[IS_VERIFIED_KEY] ?: false
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }




    companion object{
        @Volatile
        private var INSTANCE : TokenPreference? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
        private val IS_VERIFIED_KEY = booleanPreferencesKey("isVerified")

        fun getInstance(dataStore: DataStore<Preferences>): TokenPreference{
            return INSTANCE?: synchronized(this){
                val instance = TokenPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}