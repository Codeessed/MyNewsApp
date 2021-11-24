package com.androiddevs.mvvmnewsapp.repository

import com.androiddevs.mvvmnewsapp.packages.api.RetrofitInstance
import com.androiddevs.mvvmnewsapp.packages.db.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase
){
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
         RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

}