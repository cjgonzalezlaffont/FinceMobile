package com.example.fince

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppFince: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}