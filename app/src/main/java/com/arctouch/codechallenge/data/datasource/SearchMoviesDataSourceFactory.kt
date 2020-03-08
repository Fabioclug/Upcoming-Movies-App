package com.arctouch.codechallenge.data.datasource

import androidx.paging.DataSource
import com.arctouch.codechallenge.model.Movie

class SearchMoviesDataSourceFactory(query: String): DataSource.Factory<Int, Movie>() {

    private val movieDataSource = SearchMoviesDataSource(query)

    override fun create(): DataSource<Int, Movie> {
        return movieDataSource
    }

    fun finalize() {
        movieDataSource.finalize()
    }
}