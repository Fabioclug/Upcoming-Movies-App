package com.arctouch.codechallenge.data.repository

import com.arctouch.codechallenge.data.api.TmdbApi
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.MoviesResponse
import io.reactivex.Single
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


class MoviesRepository : Repository {

    private val api: TmdbApi = Retrofit.Builder()
            .baseUrl(TmdbApi.URL)
            .client(OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val original: Request = chain.request()
                        val originalHttpUrl: HttpUrl = original.url()
                        val url = originalHttpUrl.newBuilder()
                                .addQueryParameter("api_key", TmdbApi.API_KEY)
                                .build()
                        // Request customization: add request headers
                        val requestBuilder: Request.Builder = original.newBuilder()
                                .url(url)
                        val request: Request = requestBuilder.build()
                        chain.proceed(request)
                    }
                    .build()
            )
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TmdbApi::class.java)

    override fun getGenres(language: String): Single<GenreResponse> {
        return api.genres(language)
    }

    override fun getUpcomingMovies(language: String, page: Int, region: String): Single<MoviesResponse> {
        return api.upcomingMovies(language, page, region)
    }

    override fun getMovie(id: Long, language: String): Single<Movie> {
        return api.movie(id, language)
    }

    override fun getSearchMovies(language: String, query: String, page: Int, region: String): Single<MoviesResponse> {
        return api.searchMovie(language, query, page, region)
    }
}