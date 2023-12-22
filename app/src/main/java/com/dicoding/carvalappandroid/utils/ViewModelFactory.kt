package com.dicoding.carvalappandroid.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.carvalappandroid.MainViewModel
import com.dicoding.carvalappandroid.data.JobRepository
import com.dicoding.carvalappandroid.ui.about.AboutViewModel
import com.dicoding.carvalappandroid.ui.article.ArticleViewModel
import com.dicoding.carvalappandroid.ui.detail.DetailViewModel
import com.dicoding.carvalappandroid.ui.forgot.ForgotPassViewModel
import com.dicoding.carvalappandroid.ui.form.FormViewModel
import com.dicoding.carvalappandroid.ui.login.LoginViewModel
import com.dicoding.carvalappandroid.ui.onboarding.BoardingViewModel
import com.dicoding.carvalappandroid.ui.otp.OTPResetViewModel
import com.dicoding.carvalappandroid.ui.otp.OTPViewModel
import com.dicoding.carvalappandroid.ui.register.RegisterViewModel
import com.dicoding.carvalappandroid.ui.reset.PassResetViewModel
import com.dicoding.carvalappandroid.ui.splashscreen.SplashScreenViewModel

class ViewModelFactory(private val repository: JobRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }

            modelClass.isAssignableFrom(BoardingViewModel::class.java) -> {
                BoardingViewModel(repository) as T
            }

            modelClass.isAssignableFrom(AboutViewModel::class.java) -> {
                AboutViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ArticleViewModel::class.java) -> {
                ArticleViewModel(repository) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }

            modelClass.isAssignableFrom(OTPViewModel::class.java) -> {
                OTPViewModel(repository) as T
            }

            modelClass.isAssignableFrom(FormViewModel::class.java) -> {
                FormViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SplashScreenViewModel::class.java) -> {
                SplashScreenViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ForgotPassViewModel::class.java) -> {
                ForgotPassViewModel(repository) as T
            }

            modelClass.isAssignableFrom(OTPResetViewModel::class.java) -> {
                OTPResetViewModel(repository) as T
            }

            modelClass.isAssignableFrom(PassResetViewModel::class.java) -> {
                PassResetViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown VM Class : " + modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var instance : ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context, refresh : Boolean): ViewModelFactory{
            if(refresh || instance == null){
                synchronized(ViewModelFactory::class.java){
                    instance = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return instance as ViewModelFactory
        }

    }
}