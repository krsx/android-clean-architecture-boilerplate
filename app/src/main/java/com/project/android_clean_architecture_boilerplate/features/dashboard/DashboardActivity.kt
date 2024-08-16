package com.project.android_clean_architecture_boilerplate.features.dashboard

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.android_clean_architecture_boilerplate.R
import com.project.android_clean_architecture_boilerplate.databinding.ActivityDashboardBinding
import com.project.android_clean_architecture_boilerplate.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupBottomNavBar()
    }

    private fun setupBottomNavBar() {
        val bottomNavView: BottomNavigationView = binding.bottomNavigationView
        val bottomNavViewController = findNavController(R.id.nav_host_fragment)

        val bottomNavBarConfiguration = AppBarConfiguration.Builder(
            setOf(
                R.id.homeFragment,
                R.id.favoriteFragment,
                R.id.settingFragment
            )
        ).build()

        setupActionBarWithNavController(bottomNavViewController, bottomNavBarConfiguration)
        bottomNavView.setupWithNavController(bottomNavViewController)
    }

}