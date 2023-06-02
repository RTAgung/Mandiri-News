package com.example.rakaminfinaltask.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rakaminfinaltask.data.NewsRepository
import com.example.rakaminfinaltask.data.local.entity.HeadlinesNewsEntity
import com.example.rakaminfinaltask.data.Result
import com.example.rakaminfinaltask.data.local.entity.NewsEntity

class MainViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getHeadlineNews() = newsRepository.getHeadlineNews()
    fun getAllNews() = newsRepository.getAllNews()
}