package com.example.mylntapp.data.dao

import androidx.room.*
import com.example.mylntapp.data.entities.Teacher
import kotlinx.coroutines.flow.Flow

@Dao
interface TeacherDao {
    @Query("SELECT * FROM teachers ORDER BY name ASC")
    fun getAllTeachers(): Flow<List<Teacher>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeachers(teachers: List<Teacher>)

    @Query("DELETE FROM teachers")
    suspend fun deleteAll()
} 