package com.example.mylntapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylntapp.data.repository.LntRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = LntRepository(application)
    
    val news = repository.getAllNews()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun refreshNews() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.refreshNews()
            } finally {
                _isLoading.value = false
            }
        }
    }
} 