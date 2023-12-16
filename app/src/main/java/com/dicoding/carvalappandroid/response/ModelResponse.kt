package com.dicoding.carvalappandroid.response

import com.google.gson.annotations.SerializedName

data class ModelResponse(

	@field:SerializedName("real_probability")
	val realProbability: String? = null,

	@field:SerializedName("prediction")
	val prediction: String? = null,

	@field:SerializedName("fake_probability")
	val fakeProbability: String? = null
)
