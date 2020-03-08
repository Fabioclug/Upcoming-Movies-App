package com.arctouch.codechallenge.data.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.arctouch.codechallenge.data.repository.Cache
import com.arctouch.codechallenge.data.repository.Repository
import com.arctouch.codechallenge.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.util.concurrent.TimeUnit

private const val TAG = "UpcomingMoviesDataSource"

class UpcomingMoviesDataSource : PageKeyedDataSource<Int, Movie>(), KoinComponent {

    private val moviesRepository: Repository by inject()
    private var totalPages = 0
    private var disposable: Disposable? = null

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, Movie>
    ) {
        disposable = moviesRepository.getGenres()
                .flatMap {
                    Cache.cacheGenres(it.genres)
                    moviesRepository.getUpcomingMovies(page = 1)
                }
                .retryWhen { it.delay(5, TimeUnit.SECONDS) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    val moviesWithGenres = response.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    }
                    totalPages = response.totalPages
                    callback.onResult(moviesWithGenres, null, 2)
                }, {error ->
                    Log.d(TAG, "Failed to load upcoming movies", error)
                })

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        if (params.key <= totalPages) {
            disposable = moviesRepository.getUpcomingMovies(page = params.key)
                    .retryWhen { it.delay(5, TimeUnit.SECONDS) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        val moviesWithGenres = response.results.map { movie ->
                            movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                        }
                        totalPages = response.totalPages
                        callback.onResult(moviesWithGenres, params.key + 1)
                    }, {error ->
                        Log.d(TAG, "Failed to load upcoming movies", error)
                    })
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {}

    fun finalize() {
        disposable?.dispose()
    }
}