package com.arctouch.codechallenge.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.data.datasource.SearchMoviesDataSourceFactory
import com.arctouch.codechallenge.model.Movie
import io.reactivex.disposables.Disposable
import org.koin.core.parameter.parametersOf
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class SearchViewModel(query: String): ViewModel() , KoinComponent {

    private val dataSourceFactory: SearchMoviesDataSourceFactory by inject { parametersOf(query) }
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