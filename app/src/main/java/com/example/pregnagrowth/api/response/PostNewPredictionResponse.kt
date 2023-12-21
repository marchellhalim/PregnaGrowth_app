package com.example.pregnagrowth.api.response

import com.google.gson.annotations.SerializedName

data class PostNewPredictionResponse(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
