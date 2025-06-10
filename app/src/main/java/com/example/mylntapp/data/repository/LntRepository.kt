package com.example.mylntapp.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.mylntapp.data.AppDatabase
import com.example.mylntapp.data.entities.CollegeInfo
import com.example.mylntapp.data.entities.News
import com.example.mylntapp.data.entities.Teacher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.jsoup.Jsoup
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class LntRepository(context: Context) {
    private val collegeInfoDao = AppDatabase.getDatabase(context).collegeInfoDao()
    private val newsDao = AppDatabase.getDatabase(context).newsDao()
    private val teacherDao = AppDatabase.getDatabase(context).teacherDao()

    fun getCollegeInfo(): Flow<CollegeInfo?> = collegeInfoDao.getCollegeInfo()
    fun getAllNews(): Flow<List<News>> = newsDao.getAllNews()
    fun getAllTeachers(): Flow<List<Teacher>> = teacherDao.getAllTeachers()

    suspend fun refreshTeachers() {
        withContext(Dispatchers.IO) {
            try {
                val response = URL("https://www.ugrasu.ru/api/directory/lecturers").readText()
                val jsonArray = JSONArray(response)
                val teachers = mutableListOf<Teacher>()

                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    if (obj.has("chair") && obj.has("fio")) {
                        teachers.add(
                            Teacher(
                                id = obj.getString("lecturerUID") ?: obj.getString("lecturerGUID"),
                                name = obj.getString("fio"),
                                department = obj.getString("chair"),
                                email = if (obj.has("email")) obj.getString("email") else null
                            )
                        )
                    }
                }

                teacherDao.deleteAll()
                teacherDao.insertTeachers(teachers)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun refreshCollegeInfo() {
        withContext(Dispatchers.IO) {
            try {
                val doc = Jsoup.connect("https://lnt.ugrasu.ru/sveden/common/").get()
                val content = doc.select(".content").html()
                collegeInfoDao.insertCollegeInfo(CollegeInfo(content = content))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun refreshNews() {
        withContext(Dispatchers.IO) {
            try {
                val doc = Jsoup.connect("https://lnt.ugrasu.ru/news/").get()
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                
                val newsItems = doc.select(".news-item").mapNotNull { element ->
                    val dateText = element.select(".news-date").text()
                    val date = try {
                        dateFormat.parse(dateText)
                    } catch (e: Exception) {
                        null
                    }
                    
                    val calendar = Calendar.getInstance().apply {
                        if (date != null) {
                            time = date
                        }
                    }
                    
                    if (date != null && calendar.get(Calendar.YEAR) == currentYear) {
                        News(
                            id = element.hashCode(),
                            title = element.select(".news-title").text(),
                            content = element.select(".news-content").text(),
                            date = dateText,
                            imageUrl = element.select("img").firstOrNull()?.absUrl("src")
                        )
                    } else null
                }
                
                newsDao.deleteAll()
                newsDao.insertNews(newsItems)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        val LIBRARY_LINKS = listOf(
            "https://irbis.ugrasu.ru/",
            "http://znanium.com/",
            "https://www.biblio-online.ru/",
            "https://e.lanbook.com/",
            "https://www.iprbookshop.ru/"
        )

        fun openSchedule(context: Context) {
            openUrl(context, "https://itport.ugrasu.ru/")
        }

        fun openNews(context: Context) {
            openUrl(context, "https://lnt.ugrasu.ru/news/")
        }

        fun openLibraryLink(context: Context, url: String) {
            openUrl(context, url)
        }

        private fun openUrl(context: Context, url: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
} 