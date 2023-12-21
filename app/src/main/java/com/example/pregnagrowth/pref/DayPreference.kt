package com.example.pregnagrowth.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

val Context.dayDataStore: DataStore<Preferences> by preferencesDataStore(name = "day_change_handler")
class DayPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun setLastDetectedDay() {
        val currentDate = getCurrentDate()
        dataStore.edit { preferences ->
            preferences[DAY_KEY] = currentDate
        }
    }

    fun hasDayChanged(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            val savedDate = preferences[DAY_KEY] ?: ""
            val currentDate = getCurrentDate()
            savedDate != currentDate
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return dateFormat.format(Date())
    }

    companion object {
        @Volatile
        private var INSTANCE: DayPreference? = null

        private val DAY_KEY = stringPreferencesKey("last_detected_day")

        fun getInstance(dataStore: DataStore<Preferences>): DayPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = DayPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
