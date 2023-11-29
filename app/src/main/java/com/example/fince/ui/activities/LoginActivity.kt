package com.example.fince.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import com.example.fince.R
import com.example.fince.core.Config
import com.example.fince.ui.fragments.LoginFragment
import com.example.fince.ui.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        val sharedPreferences = getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        var darkMode = sharedPreferences.getBoolean("darkMode", false)

        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}