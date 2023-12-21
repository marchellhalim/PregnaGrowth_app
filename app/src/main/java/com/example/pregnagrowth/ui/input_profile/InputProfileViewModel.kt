package com.example.pregnagrowth.ui.input_profile

import androidx.lifecycle.ViewModel
import com.example.pregnagrowth.Repository

class InputProfileViewModel(private val repository: Repository): ViewModel() {
    fun profile(bio: String?, weight: String?, pregnancyAge: String?, sleepHours: String?, userId: Int?) = repository.profile(bio, weight, pregnancyAge, sleepHours, userId)
}