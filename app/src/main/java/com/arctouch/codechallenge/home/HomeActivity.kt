package com.arctouch.codechallenge.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.adapter.MoviesAdapter
import com.arctouch.codechallenge.details.MovieDetailsActivity
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.search.SearchActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.movie_list.*
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupRecyclerView()
        setupSearchButton()
        observeListChanges()
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = MoviesAdapter(cancelProgressBar = ::hideProgressBar,
                onItemClick = ::navigateToDetails)
    }

    private fun observeListChanges() {
        homeViewModel.movies.observe(this,
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

    private fun setupSearchButton() {
        searchButton.setOnClickListener {
            val searchPattern = searchEditText.text.toString()
            val searchIntent = Intent(this, SearchActivity::class.java)
            searchIntent.putExtra("searchPattern", searchPattern)
            startActivity(searchIntent)
        }
    }
}
