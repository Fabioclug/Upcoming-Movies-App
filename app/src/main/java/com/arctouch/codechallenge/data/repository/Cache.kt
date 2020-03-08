package com.arctouch.codechallenge.data.repository

import com.arctouch.codechallenge.model.Genre

object Cache {

    var genres = listOf<Genre>()

    fun cacheGenres(genres: List<Genre>) {
        Cache.genres = genres
    }
}
