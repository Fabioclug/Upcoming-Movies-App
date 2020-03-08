package com.arctouch.codechallenge.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.details.MovieDetailsActivity
import com.arctouch.codechallenge.adapter.MoviesAdapter
import com.arctouch.codechallenge.model.Movie
import kotlinx.android.synthetic.main.movie_list.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SearchActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by inject { parametersOf(intent.getStringExtra("searchPattern")) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupRecyclerView()
        observeListChanges()
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = MoviesAdapter(cancelProgressBar = ::hideProgressBar,
                onItemClick = ::navigateToDetails)
    }

    private fun observeListChanges() {
        searchViewModel.movies.observe(this,
                Observer {
                    (recyclerView.adapter as MoviesAdapter).submitList(it)
                })
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun navigateToDetails(movie: Movie) {
        val movieDetailsIntent = Intent(this, MovieDetailsActivity::class.java)
        movieDetailsIntent.putExtra("movie", movie)
        startActivity(movieDetailsIntent)
    }
}
