package com.example.rakaminfinaltask.di

import android.content.Context
import com.example.rakaminfinaltask.data.NewsRepository
import com.example.rakaminfinaltask.data.local.room.NewsDatabase
import com.example.rakaminfinaltask.data.remote.service.ApiConfig

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        return NewsRepository.getInstance(apiService, dao)
    }
}