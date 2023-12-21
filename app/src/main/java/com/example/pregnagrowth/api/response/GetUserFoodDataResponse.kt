package com.example.pregnagrowth.api.response

import com.google.gson.annotations.SerializedName

data class GetUserFoodDataResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("user")
	val user: UserInfoPrediction? = null
)

data class UserInfoPrediction(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("roleId")
	val roleId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("prediction")
	val prediction: List<PredictionItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("tanggal_lahir")
	val tanggalLahir: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class PredictionItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("lunch")
	val lunch: List<LunchItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("breakfast")
	val breakfast: List<BreakfastItem?>? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("dinner")
	val dinner: List<DinnerItem?>? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
