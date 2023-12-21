package com.example.pregnagrowth

import android.content.Context
import androidx.activity.viewModels
import androidx.datastore.preferences.protobuf.Api
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.pregnagrowth.api.ApiConfig
import com.example.pregnagrowth.api.ApiService
import com.example.pregnagrowth.api.ApiServiceFoodPrediction
import com.example.pregnagrowth.pref.UserPreference
import com.example.pregnagrowth.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams){
    private var resultStatus: Result? = null

    override fun doWork(): Result {
        val dataAge = inputData.getString(EXTRA_PREGNANCY_AGE)!!
        val userId = inputData.getString(EXTRA_USER_ID)!!
        return updatePregnancyAge(userId, dataAge)
    }

    private fun updatePregnancyAge(id: String, age: String): Result {
       TODO()
    }

    companion object {
        private val TAG = MyWorker::class.java.simpleName
        const val EXTRA_PREGNANCY_AGE = "pregnancy_age"
        const val EXTRA_USER_ID = "user_id"
    }
}