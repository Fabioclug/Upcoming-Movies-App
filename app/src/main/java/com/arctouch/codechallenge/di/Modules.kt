package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.data.datasource.UpcomingMoviesDataSourceFactory
import com.arctouch.codechallenge.data.datasource.SearchMoviesDataSourceFactory
import com.arctouch.codechallenge.data.repository.MoviesRepository
import com.arctouch.codechallenge.data.repository.Repository
import com.arctouch.codechallenge.home.HomeViewModel
import com.arctouch.codechallenge.search.SearchViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val applicationModule = module {
    factory { UpcomingMoviesDataSourceFactory() }
    factory { (query : String) -> SearchMoviesDataSourceFactory(query) }
    viewModel { (query : String) -> SearchViewModel(query) }
    viewModel { HomeViewModel() }
    single<Repository> {MoviesRepository()}
}