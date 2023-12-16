package com.example.pregnagrowth.api.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("profile")
	val profile: Profile? = null
)

data class Profile(

	@field:SerializedName("umur_janin")
	val umurJanin: Int? = null,

	@field:SerializedName("berat_badan")
	val beratBadan: Int? = null,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("jam_tidur")
	val jamTidur: Int? = null,

	@field:SerializedName("userId")
	val userId: Int? = null
)
