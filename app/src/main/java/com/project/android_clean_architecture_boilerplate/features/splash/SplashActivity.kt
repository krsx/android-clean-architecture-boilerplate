package com.project.android_clean_architecture_boilerplate.features.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.android_clean_architecture_boilerplate.R
import com.project.android_clean_architecture_boilerplate.databinding.ActivitySplashBinding
import com.project.android_clean_architecture_boilerplate.features.dashboard.DashboardActivity
import com.project.android_clean_architecture_boilerplate.features.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        checkUserToken()
    }

    private fun checkUserToken() {
        splashViewModel.getToken().observe(this){token ->
            if (token.isNullOrEmpty()){
                Handler().postDelayed({
                    val intentToHome = Intent(this, LoginActivity::class.java)
                    intentToHome.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intentToHome)
                }, DELAY.toLong())
            }else{
                Handler().postDelayed({
                    val intentToHome = Intent(this, DashboardActivity::class.java)
                    intentToHome.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intentToHome)
                }, DELAY.toLong())
            }
        }
    }

    private fun setupActionBar() {
        supportActionBar?.hide()
    }

    companion object {
        private const val DELAY = 4000
    }
}