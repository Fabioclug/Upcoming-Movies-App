package com.arctouch.codechallenge.data.datasource

import androidx.paging.DataSource
import com.arctouch.codechallenge.model.Movie

class UpcomingMoviesDataSourceFactory:
        DataSource.Factory<Int, Movie>() {

    private val movieDataSource = UpcomingMoviesDataSource()

    override fun create(): DataSource<Int, Movie> {
        return movieDataSource
    }

    fun finalize() {
        movieDataSource.finalize()
    }
}