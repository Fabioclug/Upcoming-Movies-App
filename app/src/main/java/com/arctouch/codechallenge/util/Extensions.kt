package com.arctouch.codechallenge.util

import android.widget.ImageView
import com.arctouch.codechallenge.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(imageUrl: String) {
    Glide.with(this)
            .load(imageUrl)
            .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
            .into(this)
}