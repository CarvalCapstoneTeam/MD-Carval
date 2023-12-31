package com.dicoding.carvalappandroid.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(

    @field:SerializedName("showArticle")
    val article: Article? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null

)

data class Article(

    @field:SerializedName("news_writer")
    val newsWriter: String? = null,

    @field:SerializedName("thumbnail")
    val thumbnail: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

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
