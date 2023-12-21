package com.example.pregnagrowth.api.response

import com.google.gson.annotations.SerializedName

data class FoodPredictionResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: Status? = null
)

data class Status(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("carbohydrates")
	val carbohydrates: Any? = null,

	@field:SerializedName("food_name")
	val foodName: String? = null,

	@field:SerializedName("calcium")
	val calcium: Any? = null,

	@field:SerializedName("vitamins")
	val vitamins: Any? = null,

	@field:SerializedName("protein")
	val protein: Any? = null,

	@field:SerializedName("fat")
	val fat: Any? = null,

	@field:SerializedName("accuracy")
	val accuracy: Any? = null
)
