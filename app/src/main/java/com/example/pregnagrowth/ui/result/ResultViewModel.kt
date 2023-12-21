package com.example.pregnagrowth.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.pregnagrowth.Repository
import com.example.pregnagrowth.pref.UserModel

class ResultViewModel(private val repository: Repository): ViewModel() {
    fun uploadBreakfast(predictionId: String, foodName: String?, calcium: String?, carbohydrate: String?, fat: String?, protein: String?, vitamin: String?) = repository.uploadBreakfast(predictionId, foodName, calcium, carbohydrate, fat, protein, vitamin)

    fun uploadLunch(predictionId: String, foodName: String?, calcium: String?, carbohydrate: String?, fat: String?, protein: String?, vitamin: String?) = repository.uploadLunch(predictionId, foodName, calcium, carbohydrate, fat, protein, vitamin)

    fun uploadDinner(predictionId: String, foodName: String?, calcium: String?, carbohydrate: String?, fat: String?, protein: String?, vitamin: String?) = repository.uploadDinner(predictionId, foodName, calcium, carbohydrate, fat, protein, vitamin)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}