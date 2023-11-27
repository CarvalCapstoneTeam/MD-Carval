package com.dicoding.carvalappandroid.response

data class ArticleResponse(
	val articleResponse: List<ArticleResponseItem?>? = null
)

data class ArticleResponseItem(
	val thumbnail: String? = null,
	val updatedAt: String? = null,
	val description: String? = null,
	val createdAt: String? = null,
	val id: Int? = null,
	val title: String? = null,
	val slug: String? = null,
	val content: String? = null
)

