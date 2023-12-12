package com.dicoding.carvalappandroid.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.carvalappandroid.response.DataItem

@Dao
interface JobDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: List<DataItem>)

    @Query("Select * FROM article")
    fun getAllArticle(): PagingSource<Int, DataItem>

    @Query("DELETE FROM article")
    suspend fun deleteAll()

}