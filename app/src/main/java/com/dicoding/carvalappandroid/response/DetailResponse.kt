package com.dicoding.carvalappandroid.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(

    @field:SerializedName("article")
    val article : Article? = null,

    @field:SerializedName("error")
    val error : Boolean,

    @field:SerializedName("message")
    val message : String

)

data class Article(

    @field:SerializedName("title")
    val title : String? = null,

)
