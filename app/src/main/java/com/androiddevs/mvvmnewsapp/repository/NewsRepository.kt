package com.androiddevs.mvvmnewsapp.repository

import com.androiddevs.mvvmnewsapp.packages.api.RetrofitInstance
import com.androiddevs.mvvmnewsapp.packages.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.packages.models.Article

class NewsRepository(
    val db: ArticleDatabase
){
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
         RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchForNews(querySearch: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(querySearch, pageNumber)

    suspend fun upsert(article: Article) =
        db.getArticleDao().upsert(article)

    fun getSavedArticle() =
        db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) =
        db.getArticleDao().deleteArticle(article)


}