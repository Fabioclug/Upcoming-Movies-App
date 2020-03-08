package com.arctouch.codechallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlGenerator
import com.arctouch.codechallenge.util.loadImage
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(
        private val cancelProgressBar: () -> Unit,
        private val onItemClick: (Movie) -> Unit
) : PagedListAdapter<Movie, MoviesAdapter.MovieViewHolder>(movieDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        cancelProgressBar()
        getItem(position)?.let { holder.bind(it, onItemClick) }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie, onItemClick: (Movie) -> Unit) {
            itemView.titleTextView.text = movie.title
            itemView.genresTextView.text = movie.genres?.joinToString(separator = ", ") { it.name }
            itemView.releaseDateTextView.text = movie.releaseDate

            movie.posterPath?.let {
                itemView.posterImageView.loadImage(MovieImageUrlGenerator.buildPosterUrl(it))
            }

            itemView.setOnClickListener {
                onItemClick.invoke(movie)
            }
        }
    }

    companion object {
        private val movieDiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                    oldItem == newItem
        }
    }
}
