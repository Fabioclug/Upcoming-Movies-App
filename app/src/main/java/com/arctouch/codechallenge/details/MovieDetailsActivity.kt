package com.arctouch.codechallenge.details

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlGenerator
import com.arctouch.codechallenge.util.loadImage
import kotlinx.android.synthetic.main.activity_movie_details.*


class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val movie = intent.getParcelableExtra<Movie>("movie")
        bindMovie(movie)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindMovie(movie: Movie) {
        titleTextView.text = movie.title
        genresTextView.text = movie.genres?.joinToString(separator = ", ") { it.name }
        releaseDateTextView.text = movie.releaseDate
        overviewTextView.text = movie.overview
        movie.posterPath?.let { posterImageView.loadImage(MovieImageUrlGenerator.buildPosterUrl(it)) }
        movie.backdropPath?.let { backdropImageView.loadImage(MovieImageUrlGenerator.buildBackdropUrl(it)) }
    }

}
