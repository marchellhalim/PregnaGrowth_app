package com.example.pregnagrowth.api.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(

	@field:SerializedName("data")
	val data: DataCount? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataCount(

	@field:SerializedName("count")
	val count: Int? = null
)
