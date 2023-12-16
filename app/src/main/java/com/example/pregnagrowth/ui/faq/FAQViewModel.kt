package com.example.pregnagrowth.ui.faq

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.pregnagrowth.Repository
import com.example.pregnagrowth.api.response.FaqsItem

class FAQViewModel(private val repository: Repository): ViewModel() {
    fun FAQs() = repository.getFAQs()
}