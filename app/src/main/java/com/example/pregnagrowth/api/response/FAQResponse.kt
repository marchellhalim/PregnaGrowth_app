package com.example.pregnagrowth.api.response

import com.google.gson.annotations.SerializedName

data class FAQResponse(

	@field:SerializedName("faqs")
	val faqs: List<FaqsItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

data class FaqsItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("answer")
	val answer: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
