package com.androiddevs.mvvmnewsapp.packages.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.packages.models.Article
import com.androiddevs.mvvmnewsapp.packages.models.NewsResponse
import com.androiddevs.mvvmnewsapp.packages.util.Resource
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    app: Application,
    val newsRepository: NewsRepository
): ViewModel() {
    val breakingNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse:NewsResponse? = null

    val searchNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse:NewsResponse? = null

    init {
        getBreakingNews("ng")
    }

    fun getBreakingNews(countryCode: String)= viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(getBreakingNewsResponse(response))
    }
    fun searchForNews(searchQuery: String)= viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchForNews(searchQuery, searchNewsPage)
        searchNews.postValue(getSearchNewsResponse(response))
    }

    private fun getBreakingNewsResponse(response : Response<NewsResponse>):Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if(breakingNewsResponse == null){
                    breakingNewsResponse = resultResponse
                }else{
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())

    }

    private fun getSearchNewsResponse(response : Response<NewsResponse>):Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let {resultResponse ->
                searchNewsPage++
                if(searchNewsResponse == null){
                    searchNewsResponse = resultResponse
                }else{
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())

    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }
    fun getSavedNews() = newsRepository.getSavedArticle()

    fun deleteArticle(article:Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }



}