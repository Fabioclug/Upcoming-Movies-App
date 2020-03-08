package com.arctouch.codechallenge.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.data.datasource.UpcomingMoviesDataSourceFactory
import com.arctouch.codechallenge.model.Movie
import io.reactivex.disposables.Disposable
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class HomeViewModel : ViewModel(), KoinComponent {

    private val dataSourceFactory: UpcomingMoviesDataSourceFactory by inject()
    private var disposable: Disposable? = null
    val movies: LiveData<PagedList<Movie>>

    init {
        val pagedListConfig = PagedList.Config.Builder()
                .setPageSize(1)
                .setEnablePlaceholders(true)
                .build()
        movies = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}