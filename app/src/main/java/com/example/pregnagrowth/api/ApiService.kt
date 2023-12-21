package com.example.pregnagrowth.api

import com.example.pregnagrowth.api.response.FAQResponse
import com.example.pregnagrowth.api.response.GetUserByIdResponse
import com.example.pregnagrowth.api.response.GetUserFoodDataResponse
import com.example.pregnagrowth.api.response.LoginResponse
import com.example.pregnagrowth.api.response.PostBreakfastResponse
import com.example.pregnagrowth.api.response.PostDinnerResponse
import com.example.pregnagrowth.api.response.PostLunchResponse
import com.example.pregnagrowth.api.response.PostNewPredictionResponse
import com.example.pregnagrowth.api.response.ProfileResponse
import com.example.pregnagrowth.api.response.SignupResponse
import com.example.pregnagrowth.api.response.UpdateProfileResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiService {
    @FormUrlEncoded
    @POST("v1/auth/register")
    suspend fun signup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("username") username: String,
        @Field("tanggal_lahir") birthdate: String,
        @Field("roleId") roleId: Int = 2
    ): SignupResponse

    @FormUrlEncoded
    @POST("v1/profile")
    suspend fun profile(
        @Field("bio") bio: String? = null,
        @Field("berat_badan") weight: String? = null,
        @Field("umur_janin") pregnancyAge: String? = null,
        @Field("jam_tidur") sleepHours: String? = null,
        @Field("userId") userId: Int? = null
    ): ProfileResponse

    @FormUrlEncoded
    @POST("v1/auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse

    @GET("v1/user/profile/{id}")
    suspend fun getUserInfo(
        @Path("id") id: String // User ID
    ): GetUserByIdResponse

    @FormUrlEncoded
    @PUT("v1/user//user/{id}/profile")
    suspend fun updateProfile(
        @Path("id") id: String, // User ID
        @Field("umur_janin") updatedPregnancyAge: String? = null
    ): UpdateProfileResponse

    // Post a new Prediction with associated User ID
    @FormUrlEncoded
    @POST("v1/prediksi")
    suspend fun uploadNewPrediction(
        @Field("userId") id: String
    ): PostNewPredictionResponse

    // Post breakfast into Prediction with its Prediction ID
    @FormUrlEncoded
    @POST("v1/prediksi/breakfast/{id}")
    suspend fun uploadBreakfast(
        @Path("id") id: String,
        @Field("food_name") foodName: String? = null,
        @Field("calcium") calcium: String? = null,
        @Field("carbohydrate") carbohydrate: String? = null,
        @Field("fat") fat: String? = null,
        @Field("protein") protein: String? = null,
        @Field("vitamin") vitamin: String? = null
    ): PostBreakfastResponse

    // Post lunch into Prediction with its Prediction ID
    @FormUrlEncoded
    @POST("v1/prediksi/lunch/{id}")
    suspend fun uploadLunch(
        @Path("id") id: String,
        @Field("food_name") foodName: String? = null,
        @Field("calcium") calcium: String? = null,
        @Field("carbohydrate") carbohydrate: String? = null,
        @Field("fat") fat: String? = null,
        @Field("protein") protein: String? = null,
        @Field("vitamin") vitamin: String? = null
    ): PostLunchResponse

    // Post dinner into Prediction with its Prediction ID
    @FormUrlEncoded
    @POST("v1/prediksi/dinner/{id}")
    suspend fun uploadDinner(
        @Path("id") id: String,
        @Field("food_name") foodName: String? = null,
        @Field("calcium") calcium: String? = null,
        @Field("carbohydrate") carbohydrate: String? = null,
        @Field("fat") fat: String? = null,
        @Field("protein") protein: String? = null,
        @Field("vitamin") vitamin: String? = null
    ): PostDinnerResponse

    // Get User's all food data by User ID
    @GET("v1/user/prediksi/{id}")
    suspend fun getUserFoodData(
        @Path("id") userId: String
    ): GetUserFoodDataResponse

    @GET("v1/question")
    suspend fun faqList(): FAQResponse
}