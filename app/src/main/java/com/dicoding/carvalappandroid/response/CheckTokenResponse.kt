package com.dicoding.carvalappandroid.response

import com.google.gson.annotations.SerializedName

data class CheckTokenResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
