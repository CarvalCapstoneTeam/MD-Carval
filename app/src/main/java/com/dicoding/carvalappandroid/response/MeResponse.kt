package com.dicoding.carvalappandroid.response

import com.google.gson.annotations.SerializedName

data class MeResponse(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
