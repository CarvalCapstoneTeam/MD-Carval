package com.dicoding.carvalappandroid.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.carvalappandroid.response.HomeDataItem

@Dao
interface HomeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: List<HomeDataItem>)

    @Query("Select * from home_article LIMIT 5")
    fun getAllArticle(): LiveData<List<HomeDataItem>>

    @Query("DELETE FROM home_article")
    suspend fun clearAllArticles()

}