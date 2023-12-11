package com.dicoding.carvalappandroid.response

import com.google.gson.annotations.SerializedName

data class OTPResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
