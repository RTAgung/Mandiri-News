package com.example.rakaminfinaltask.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.rakaminfinaltask.data.local.entity.HeadlinesNewsEntity
import com.example.rakaminfinaltask.data.local.entity.NewsEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ORDER BY publishedAt DESC")
    fun getAllNews(): LiveData<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllNews(news: List<NewsEntity>)

    @Query("DELETE FROM news")
    suspend fun deleteAllNews()

    @Query("SELECT * FROM headlines_news")
    fun getHeadlinesNews(): LiveData<List<HeadlinesNewsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHeadlinesNews(news: List<HeadlinesNewsEntity>)

    @Query("DELETE FROM headlines_news")
    suspend fun deleteHeadlinesNews()
}