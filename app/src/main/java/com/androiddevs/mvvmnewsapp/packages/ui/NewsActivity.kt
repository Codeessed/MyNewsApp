package com.androiddevs.mvvmnewsapp.packages.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.packages.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.packages.viewmodel.NewsViewModel
import com.androiddevs.mvvmnewsapp.packages.viewmodel.NewsViewModelFactory
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val newsRepository = NewsRepository(ArticleDatabase(this))
        val newsViewModelProvider = NewsViewModelFactory(application, newsRepository)
        viewModel = ViewModelProvider(this, newsViewModelProvider).get(NewsViewModel::class.java)

        //set up bottom navigation with fragments
        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
    }
}
