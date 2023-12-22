package com.dicoding.carvalappandroid.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.carvalappandroid.response.HomeDataItem

@Database(
    entities = [HomeDataItem::class],
    version = 1,
    exportSchema = false
)
abstract class HomeDatabase : RoomDatabase() {

    abstract fun homeDao() : HomeDAO

    companion object{
        @Volatile
        private var INSTANCE : HomeDatabase? = null

        @JvmStatic
        fun getHomeDatabase(context: Context): HomeDatabase{
            return INSTANCE?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    HomeDatabase::class.java, "home_job_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}