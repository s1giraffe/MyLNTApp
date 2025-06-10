package com.example.mylntapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "college_info")
data class CollegeInfo(
    @PrimaryKey
    val id: Int = 1,
    val content: String,
    val lastUpdated: Long = System.currentTimeMillis()
) 