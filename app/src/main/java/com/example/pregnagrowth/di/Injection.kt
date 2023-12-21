package com.example.pregnagrowth.di

import android.content.Context
import com.example.pregnagrowth.Repository
import com.example.pregnagrowth.api.ApiConfig
import com.example.pregnagrowth.api.ApiConfigFoodPrediction
import com.example.pregnagrowth.api.ApiServiceFoodPrediction
import com.example.pregnagrowth.pref.DayPreference
import com.example.pregnagrowth.pref.UserPreference
import com.example.pregnagrowth.pref.dataStore
import com.example.pregnagrowth.pref.dayDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = UserPreference.getInstance(context.dataStore)
        val dayPref = DayPreference.getInstance(context.dayDataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        val apiServiceFoodPrediction = ApiConfigFoodPrediction.getApiService()
        return Repository.getInstance(pref, dayPref, apiService, apiServiceFoodPrediction)
    }
}