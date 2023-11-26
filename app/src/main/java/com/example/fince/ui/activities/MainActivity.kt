package com.example.fince.ui.activities

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.fince.R
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import com.example.fince.ui.viewmodel.SharedViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        val sharedPreferences = getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "")!!

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.hamburger)

        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(navigationView, navController)

        val headerUsernameTextView: TextView = navigationView.getHeaderView(0).findViewById(R.id.lblUsuario)
        val headerUserIdTextView: TextView = navigationView.getHeaderView(0).findViewById(R.id.lblId)

        sharedViewModel.getUser(userId)

        sharedViewModel.response.observe(this) { user ->
            headerUsernameTextView.text = (user.nombre + " " + user.apellido)
            headerUserIdTextView.text = user.userId
        }

        var darkMode = sharedPreferences.getBoolean("darkMode", false)

        sharedViewModel.setDarkMode(darkMode)

        sharedViewModel.isDarkMode.observe(this) { isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }
}