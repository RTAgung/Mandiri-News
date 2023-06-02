package com.example.rakaminfinaltask.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.rakaminfinaltask.data.local.entity.HeadlinesNewsEntity
import com.example.rakaminfinaltask.data.local.entity.NewsEntity
import com.example.rakaminfinaltask.data.local.room.NewsDao
import com.example.rakaminfinaltask.data.remote.service.ApiService

class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao
) {
    fun getHeadlineNews(): LiveData<Result<List<HeadlinesNewsEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getNewsTopHeadlines()
            val articles = response.articles
            val newsList = ArrayList<HeadlinesNewsEntity>()
            articles?.let { itemArticles ->
                itemArticles.map { article ->
                    article?.let { itemArticle ->
                        newsList.add(
                            HeadlinesNewsEntity(
                                title = itemArticle.title,
                                publishedAt = itemArticle.publishedAt,
                                urlToImage = itemArticle.urlToImage,
                                url = itemArticle.url,
                                publisher_name = itemArticle.source?.name,
                                description = itemArticle.description,
                                content = itemArticle.content
                            )
                        )
                    }
                }
            }
            newsDao.deleteHeadlinesNews()
            newsDao.insertHeadlinesNews(newsList)
        } catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<HeadlinesNewsEntity>>> =
            newsDao.getHeadlinesNews().map { Result.Success(it) }
        emitSource(localData)
    }

    fun getAllNews(): LiveData<Result<List<NewsEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllNews()
            val articles = response.articles
            val newsList = ArrayList<NewsEntity>()
            articles?.let { itemArticles ->
                itemArticles.map { article ->
                    article?.let { itemArticle ->
                        newsList.add(
                            NewsEntity(
                                title = itemArticle.title,
                                publishedAt = itemArticle.publishedAt,
                                urlToImage = itemArticle.urlToImage,
                                url = itemArticle.url,
                                publisher_name = itemArticle.source?.name,
                                description = itemArticle.description,
                                content = itemArticle.content
                            )
                        )
                    }
                }
            }
            newsDao.deleteAllNews()
            newsDao.insertAllNews(newsList)
        } catch (e: Exception) {
            Log.d("NewsRepository", "getAllNews: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<NewsEntity>>> =
            newsDao.getAllNews().map { Result.Success(it) }
        emitSource(localData)
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao)
            }.also { instance = it }
    }
}