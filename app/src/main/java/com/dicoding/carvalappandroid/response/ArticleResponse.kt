package com.dicoding.carvalappandroid.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ArticleResponse(
	@field:SerializedName("listArticle")
	val listArticle: List<ArticleResponseItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Entity(tableName = "article")
data class ArticleResponseItem(

	@field:SerializedName("thumbnail")
	val thumbnail: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@PrimaryKey
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("source_date")
	val sourceDate: String? = null
)

