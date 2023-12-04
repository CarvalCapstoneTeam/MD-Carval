package com.dicoding.carvalappandroid.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.carvalappandroid.remotekeys.RemoteKeys
import com.dicoding.carvalappandroid.remotekeys.RemoteKeysDAO
import com.dicoding.carvalappandroid.response.ArticleResponseItem

@Database(
    entities = [ArticleResponseItem::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class JobDatabase : RoomDatabase() {
    abstract fun jobDAO() : JobDAO
    abstract fun remoteKeysDAO() : RemoteKeysDAO

    companion object{
        @Volatile
        private var INSTANCE : JobDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): JobDatabase{
            return INSTANCE?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    JobDatabase::class.java, "job_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}