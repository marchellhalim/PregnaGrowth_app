package com.example.pregnagrowth

import androidx.lifecycle.liveData
import com.example.pregnagrowth.api.ApiService
import com.example.pregnagrowth.api.response.LoginResponse
import com.example.pregnagrowth.api.response.SignupResponse
import com.example.pregnagrowth.pref.UserModel
import com.example.pregnagrowth.pref.UserPreference
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class Repository(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
) {
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun signup(name: String, email: String, password: String, username: String, birthdate: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.signup(name, email, password, username, birthdate).success.toString()
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, SignupResponse::class.java)
            emit(ResultState.Error(errorResponse.success.toString()))
        }
    }

    fun login(email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.login(email, password)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(ResultState.Error(errorResponse.success.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            //storiesDatabase: StoriesDatabase,
            userPreference: UserPreference,
            apiService: ApiService
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(/*storiesDatabase, */userPreference, apiService)
            }.also { instance = it }
    }
}