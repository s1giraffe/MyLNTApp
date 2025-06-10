package com.example.mylntapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylntapp.data.entities.CollegeInfo
import com.example.mylntapp.data.repository.LntRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class InfoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = LntRepository(application)
    
    val collegeInfo = repository.getCollegeInfo()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun refreshInfo() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.refreshCollegeInfo()
            } finally {
                _isLoading.value = false
            }
        }
    }
} 