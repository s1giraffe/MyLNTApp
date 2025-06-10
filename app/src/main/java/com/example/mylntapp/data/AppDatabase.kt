package com.example.mylntapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mylntapp.data.dao.CollegeInfoDao
import com.example.mylntapp.data.dao.NewsDao
import com.example.mylntapp.data.dao.TeacherDao
import com.example.mylntapp.data.entities.CollegeInfo
import com.example.mylntapp.data.entities.News
import com.example.mylntapp.data.entities.Teacher

@Database(entities = [CollegeInfo::class, News::class, Teacher::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun collegeInfoDao(): CollegeInfoDao
    abstract fun newsDao(): NewsDao
    abstract fun teacherDao(): TeacherDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lnt_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 