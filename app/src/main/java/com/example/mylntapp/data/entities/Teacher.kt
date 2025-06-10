package com.example.mylntapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teachers")
data class Teacher(
    @PrimaryKey
    val id: String,
    val name: String,
    val department: String,
    val email: String?,
    val lastUpdated: Long = System.currentTimeMillis()
) 