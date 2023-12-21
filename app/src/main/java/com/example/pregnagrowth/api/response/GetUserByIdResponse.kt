package com.example.pregnagrowth.api.response

import com.google.gson.annotations.SerializedName

data class GetUserByIdResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("user")
	val user: UserInfo? = null
)

data class UserInfo(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("roleId")
	val roleId: Int? = null,

	@field:SerializedName("profile")
	val profile: List<Profile?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

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
