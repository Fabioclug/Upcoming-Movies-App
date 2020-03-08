package com.arctouch.codechallenge

import android.app.Application
import com.arctouch.codechallenge.di.applicationModule
import org.koin.android.ext.android.startKoin

class UpcomingMoviesApplication: Application()  {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(applicationModule))
    }
}