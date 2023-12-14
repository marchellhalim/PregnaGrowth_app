package com.example.pregnagrowth.api

import com.example.pregnagrowth.api.response.LoginResponse
import com.example.pregnagrowth.api.response.SignupResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
    @POST("v1/auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse
}