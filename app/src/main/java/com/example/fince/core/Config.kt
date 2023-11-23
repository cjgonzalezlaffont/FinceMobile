package com.example.fince.core

import androidx.appcompat.app.AppCompatDelegate

object Config {
    var dark_mode: Boolean = false

    fun setDarkMode(){
        if (dark_mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    lateinit var apiKey: String
    lateinit var baseUrl: String
}

