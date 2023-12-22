package com.dicoding.carvalappandroid.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.carvalappandroid.data.HomeRepository
import com.dicoding.carvalappandroid.ui.home.HomeViewModel


class ViewModelFactoryHome(private val repository: HomeRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown VM Class : " + modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var instance : ViewModelFactoryHome? = null

        @JvmStatic
        fun getInstanceHome(context: Context, refresh : Boolean): ViewModelFactoryHome{
            if(refresh || instance == null){
                synchronized(ViewModelFactory::class.java){
                    instance = ViewModelFactoryHome(Injection.provideRepository2(context))
                }
            }
            return instance as ViewModelFactoryHome
        }
    }
}