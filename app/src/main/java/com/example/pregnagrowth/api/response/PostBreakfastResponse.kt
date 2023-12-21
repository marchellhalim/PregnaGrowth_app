package com.example.pregnagrowth.api.response

import com.google.gson.annotations.SerializedName

data class PostBreakfastResponse(

	@field:SerializedName("result")
	val result: BreakfastItem? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

data class BreakfastItem(

	@field:SerializedName("food_name")
	val foodName: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("calcium")
	val calcium: Any? = null,

	@field:SerializedName("protein")
	val protein: Any? = null,

	@field:SerializedName("fat")
	val fat: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("predictionId")
	val predictionId: Int? = null,

	@field:SerializedName("carbohydrate")
	val carbohydrate: Any? = null,

	@field:SerializedName("vitamin")
	val vitamin: Any? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
