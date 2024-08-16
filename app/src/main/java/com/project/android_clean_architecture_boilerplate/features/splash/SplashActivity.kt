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
import com.project.core.utils.NO_DATA
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val activityNameTag = "SplashActivity"

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
            Timber.tag(activityNameTag).d("Token $token")

            if (token.isNullOrEmpty() || token == NO_DATA){
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

    companion object {
        private const val DELAY = 4000
    }
}