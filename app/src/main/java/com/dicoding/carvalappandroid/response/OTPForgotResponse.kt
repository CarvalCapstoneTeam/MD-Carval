package com.dicoding.carvalappandroid.response

import com.google.gson.annotations.SerializedName

data class OTPForgotResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
