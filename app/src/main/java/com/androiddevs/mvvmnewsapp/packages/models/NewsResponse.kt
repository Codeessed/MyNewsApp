package com.androiddevs.mvvmnewsapp.packages.models

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)