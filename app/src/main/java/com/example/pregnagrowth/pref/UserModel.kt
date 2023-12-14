package com.example.pregnagrowth.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)
