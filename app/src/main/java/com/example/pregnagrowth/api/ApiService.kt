package com.example.pregnagrowth.api

import com.example.pregnagrowth.api.response.FAQResponse
import com.example.pregnagrowth.api.response.LoginResponse
import com.example.pregnagrowth.api.response.ProfileResponse
import com.example.pregnagrowth.api.response.SignupResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


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
        @Field("berat_badan") weight: Int? = null,
        @Field("umur_janin") pregnancyAge: Int? = null,
        @Field("jam_tidur") sleepHours: Int? = null,
        @Field("userId") userId: Int? = null
    ): ProfileResponse

    /*@POST("v1/profile")
    suspend fun profile(
        @Body bio: RequestBody?,
        @Body weight: RequestBody?,
        @Body pregnancyAge: RequestBody?,
        @Body sleepHours: RequestBody?,
        @Body userId: RequestBody?
    ): ProfileResponse */

    @FormUrlEncoded
    @POST("v1/auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse

    @GET("v1/question")
    suspend fun faqList(): FAQResponse
}