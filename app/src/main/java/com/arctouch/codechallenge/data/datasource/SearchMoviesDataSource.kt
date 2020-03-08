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

private const val TAG = "SearchMoviesDataSource"

class SearchMoviesDataSource(private val query: String): PageKeyedDataSource<Int, Movie>(), KoinComponent {

    private val moviesRepository: Repository by inject()
    private var totalPages = 0
    private var disposable: Disposable? = null

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, Movie>
    ) {
        loadMovies(1) { movies, page ->
            callback.onResult(movies, null, page)
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        if (params.key <= totalPages) {
            loadMovies(params.key) { movies, page ->
                callback.onResult(movies, page + 1)
            }
        }
    }

    private fun loadMovies(page: Int, callback: (List<Movie>, Int) -> Unit) {
        disposable = moviesRepository.getSearchMovies(query = query, page = page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen { it.delay(5, TimeUnit.SECONDS) }
                .subscribe({ response ->
                    val moviesWithGenres = response.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    }
                    totalPages = response.totalPages
                    callback(moviesWithGenres, page)
                }, {error ->
                    Log.d(TAG, "Failed to search movies", error)
                })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {}

    fun finalize() {
        disposable?.dispose()
    }
}