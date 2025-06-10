package com.example.mylntapp.data.dao

import androidx.room.*
import com.example.mylntapp.data.entities.CollegeInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface CollegeInfoDao {
    @Query("SELECT * FROM college_info WHERE id = 1")
    fun getCollegeInfo(): Flow<CollegeInfo?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollegeInfo(info: CollegeInfo)

    @Query("DELETE FROM college_info")
    suspend fun deleteAll()
} 