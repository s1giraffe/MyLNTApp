package com.example.mylntapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylntapp.data.entities.Teacher
import com.example.mylntapp.data.repository.LntRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TeachersViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = LntRepository(application)
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    
    private val allTeachers = repository.getAllTeachers()
    
    val teachers = combine(allTeachers, searchQuery) { teachers, query ->
        if (query.isEmpty()) {
            teachers
        } else {
            teachers.filter { teacher ->
                teacher.name.contains(query, ignoreCase = true) ||
                teacher.department.contains(query, ignoreCase = true) ||
                (teacher.email?.contains(query, ignoreCase = true) == true)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun refreshTeachers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.refreshTeachers()
            } finally {
                _isLoading.value = false
            }
        }
    }
} 