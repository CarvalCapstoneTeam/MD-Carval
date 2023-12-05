package com.dicoding.carvalappandroid.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.carvalappandroid.response.ArticleResponseItem

@Dao
interface JobDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: List<ArticleResponseItem?>?)

    @Query("Select * FROM ARTICLE")
    fun getAllArticle(): PagingSource<Int, ArticleResponseItem>

    @Query("DELETE FROM article")
    suspend fun deleteAll()

}