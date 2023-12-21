package com.example.pregnagrowth.ui.detail_intake

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.pregnagrowth.Repository
import com.example.pregnagrowth.pref.UserModel
import java.io.File

class DetailIntakeViewModel(private val repository: Repository) : ViewModel() {
    fun uploadPhoto(imageFile: File) = repository.uploadPhoto(imageFile)

    fun uploadNewPrediction(userId: String) = repository.uploadNewPrediction(userId)

    fun getUserFoodData(userId: String) = repository.getUserFoodData(userId)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}