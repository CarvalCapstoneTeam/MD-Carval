package com.dicoding.carvalappandroid.response

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ArticleResponse(
	val articleResponse: List<ArticleResponseItem?>? = null
)

@Entity(tableName = "article")
data class ArticleResponseItem(
	val thumbnail: String? = null,
	val updatedAt: String? = null,
	val description: String? = null,
	val createdAt: String? = null,
	@PrimaryKey
	val id: Int ,
	val title: String? = null,
	val slug: String? = null,
	val content: String? = null
)

