package com.example.pregnagrowth.ui.signup

import androidx.lifecycle.ViewModel
import com.example.pregnagrowth.Repository

class SignupViewModel(private val repository: Repository): ViewModel() {
    fun signup(name: String, email: String, password: String, username: String, birthdate: String) = repository.signup(name, email, password, username, birthdate)
}