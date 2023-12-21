package com.example.pregnagrowth

import androidx.lifecycle.liveData
import com.example.pregnagrowth.api.ApiConfigFoodPrediction
import com.example.pregnagrowth.api.ApiService
import com.example.pregnagrowth.api.ApiServiceFoodPrediction
import com.example.pregnagrowth.api.response.FAQResponse
import com.example.pregnagrowth.api.response.FoodPredictionResponse
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
import com.example.pregnagrowth.pref.DayPreference
import com.example.pregnagrowth.pref.UserModel
import com.example.pregnagrowth.pref.UserPreference
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class Repository(
    private val userPreference: UserPreference,
    private val dayPreference: DayPreference,
    private val apiService: ApiService,
    private val apiServiceFoodPrediction: ApiServiceFoodPrediction
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

    suspend fun setLastDetectedDay() {
        dayPreference.setLastDetectedDay()
    }

    fun hasDayChanged(): Flow<Boolean> {
        return dayPreference.hasDayChanged()
    }

    fun signup(name: String, email: String, password: String, username: String, birthdate: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.signup(name, email, password, username, birthdate)
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

    fun profile(bio: String?, weight: String?, pregnancyAge: String?, sleepHours: String?, userId: Int?) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.profile(bio, weight, pregnancyAge, sleepHours, userId)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ProfileResponse::class.java)
            emit(ResultState.Error(errorResponse.success.toString()))
        }
    }

    fun getUserInfo(id: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getUserInfo(id)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, GetUserByIdResponse::class.java)
            emit(ResultState.Error(errorResponse.success.toString()))
        }
    }

    fun updateProfile(id: String, updatedPregnancyAge: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.updateProfile(id, updatedPregnancyAge)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, UpdateProfileResponse::class.java)
            emit(ResultState.Error(errorResponse.success.toString()))
        }
    }

    fun uploadNewPrediction(id: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.uploadNewPrediction(id)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, PostNewPredictionResponse::class.java)
            emit(ResultState.Error(errorResponse.userId.toString()))
        }
    }

    fun uploadBreakfast(predictionId: String, foodName: String?, calcium: String?, carbohydrate: String?, fat: String?, protein: String?, vitamin: String?) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.uploadBreakfast(predictionId, foodName, calcium, carbohydrate, fat, protein, vitamin)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, PostBreakfastResponse::class.java)
            emit(ResultState.Error(errorResponse.success.toString()))
        }
    }

    fun uploadLunch(predictionId: String, foodName: String?, calcium: String?, carbohydrate: String?, fat: String?, protein: String?, vitamin: String?) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.uploadLunch(predictionId, foodName, calcium, carbohydrate, fat, protein, vitamin)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, PostLunchResponse::class.java)
            emit(ResultState.Error(errorResponse.success.toString()))
        }
    }

    fun uploadDinner(predictionId: String, foodName: String?, calcium: String?, carbohydrate: String?, fat: String?, protein: String?, vitamin: String?) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.uploadDinner(predictionId, foodName, calcium, carbohydrate, fat, protein, vitamin)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, PostDinnerResponse::class.java)
            emit(ResultState.Error(errorResponse.success.toString()))
        }
    }

    fun getUserFoodData(userId: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getUserFoodData(userId)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, GetUserFoodDataResponse::class.java)
            emit(ResultState.Error(errorResponse.success.toString()))
        }
    }

    fun uploadPhoto(imageFile: File) = liveData {
        emit(ResultState.Loading)
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiServiceFoodPrediction.predictFood(multipartBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FoodPredictionResponse::class.java)
            emit(ResultState.Error(errorResponse.status?.message!!))
        }
    }

    fun getFAQs() = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.faqList()
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FAQResponse::class.java)
            emit(ResultState.Error(errorResponse.success.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            userPreference: UserPreference,
            dayPreference: DayPreference,
            apiService: ApiService,
            apiServiceFoodPrediction: ApiServiceFoodPrediction
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(userPreference, dayPreference, apiService, apiServiceFoodPrediction)
            }.also { instance = it }
    }
}