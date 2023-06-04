package com.example.rakaminfinaltask.ui

import androidx.lifecycle.ViewModel
import com.example.rakaminfinaltask.data.NewsRepository

class MainViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getHeadlineNews() = newsRepository.getHeadlineNews()
    fun getAllNews() = newsRepository.getAllNews()
}