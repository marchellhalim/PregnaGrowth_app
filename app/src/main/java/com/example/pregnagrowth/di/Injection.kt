package com.example.pregnagrowth.di

import android.content.Context
import com.example.pregnagrowth.Repository
import com.example.pregnagrowth.api.ApiConfig
import com.example.pregnagrowth.pref.UserPreference
import com.example.pregnagrowth.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        //val database = StoriesDatabase.getDatabase(context)
        return Repository.getInstance(/*database, */pref, apiService)
    }
}