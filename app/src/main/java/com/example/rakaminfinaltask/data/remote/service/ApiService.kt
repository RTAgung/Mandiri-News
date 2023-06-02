package com.example.rakaminfinaltask.data.remote.service

import com.example.rakaminfinaltask.data.remote.response.NewsResponse
import com.example.rakaminfinaltask.data.remote.service.ApiConfig.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines?country=us&pageSize=10&apiKey=$API_KEY")
    suspend fun getNewsTopHeadlines(): NewsResponse

    @GET("everything?language=en&pageSize=30&domains=techcrunch.com,thenextweb.com,bbc.co.uk,engadget.com&apiKey=$API_KEY")
    suspend fun getAllNews(): NewsResponse
}