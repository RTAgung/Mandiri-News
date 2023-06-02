package com.example.rakaminfinaltask.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rakaminfinaltask.data.local.entity.HeadlinesNewsEntity
import com.example.rakaminfinaltask.data.local.entity.NewsEntity

@Database(entities = [NewsEntity::class, HeadlinesNewsEntity::class], version = 2)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var instance: NewsDatabase? = null
        fun getInstance(context: Context): NewsDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java, "News.db"
                ).allowMainThreadQueries()
                    .build()
            }
    }
}