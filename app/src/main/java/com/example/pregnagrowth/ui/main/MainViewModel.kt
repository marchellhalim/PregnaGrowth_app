package com.example.pregnagrowth.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pregnagrowth.Repository
import com.example.pregnagrowth.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {
    fun getUserInfo(id: String) = repository.getUserInfo(id)

    fun updateProfile(id: String, updatedPregnancyAge: String) = repository.updateProfile(id, updatedPregnancyAge)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getUserFoodData(userId: String) = repository.getUserFoodData(userId)

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}