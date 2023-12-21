package com.example.pregnagrowth.api

import com.example.pregnagrowth.api.response.FoodPredictionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServiceFoodPrediction {
    @Multipart
    @POST("prediction")
    suspend fun predictFood(
        @Part image: MultipartBody.Part
    ): FoodPredictionResponse

}