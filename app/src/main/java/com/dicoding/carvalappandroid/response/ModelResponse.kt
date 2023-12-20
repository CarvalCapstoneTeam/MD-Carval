package com.dicoding.carvalappandroid.response

import com.google.gson.annotations.SerializedName

data class ModelResponse(

	@field:SerializedName("predictionResult")
	val predictionResult: PredictionResult? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class PredictionResult(

	@field:SerializedName("real_probability")
	val realProbability: Any? = null,

	@field:SerializedName("prediction")
	val prediction: String? = null,

	@field:SerializedName("fake_probability")
	val fakeProbability: Any? = null
)
