package com.arctouch.codechallenge.data.repository

import com.arctouch.codechallenge.data.api.TmdbApi
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.MoviesResponse
import io.reactivex.Single

interface Repository {

    fun getGenres(language: String = TmdbApi.DEFAULT_LANGUAGE): Single<GenreResponse>

    fun getUpcomingMovies(language: String = TmdbApi.DEFAULT_LANGUAGE, page: Int, region: String = TmdbApi.DEFAULT_REGION): Single<MoviesResponse>

    fun getMovie(id: Long, language: String = TmdbApi.DEFAULT_LANGUAGE): Single<Movie>

    fun getSearchMovies(language: String = TmdbApi.DEFAULT_LANGUAGE, query: String, page: Int, region: String = TmdbApi.DEFAULT_REGION): Single<MoviesResponse>
}