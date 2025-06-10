package com.example.mylntapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(
    @PrimaryKey
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    val imageUrl: String?,
    val lastUpdated: Long = System.currentTimeMillis()
) 