package com.project.android_clean_architecture_boilerplate.features.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.android_clean_architecture_boilerplate.databinding.ActivityLoginBinding
import com.project.android_clean_architecture_boilerplate.features.dashboard.DashboardActivity
import com.project.android_clean_architecture_boilerplate.features.register.RegisterActivity
import com.project.core.data.source.Resource
import com.project.core.domain.model.Auth
import com.project.core.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val activityNameTag = "LoginActivity"

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        isButtonRegisterEnabled(false)

        handleFormInputs()

        handleLoginButton()

        handleRegisterHereButton()
    }

    private fun handleRegisterHereButton() {
        binding.tvToRegister.setOnClickListener {
            val intentToRegister = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intentToRegister)
        }
    }

    private fun handleLoginButton() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            loginViewModel.loginUser(email, password).observe(this) { result ->
                when (result) {
                    is Resource.Error -> {
                        showLoading(false)
                        Timber.tag(activityNameTag).e("Error : ${result.message}")
                        showToast(result.message.toString())
                    }

                    is Resource.Loading -> {
                        showLoading(true)
                    }

                    is Resource.Message -> {
                        showLoading(false)
                        Timber.tag(activityNameTag).e("Message : ${result.message}")
                    }

                    is Resource.Success -> {
                        showLoading(false)
                        Timber.tag(activityNameTag).d("Success")

                        saveData(result.data)
                    }
                }
            }
        }
    }

    private fun saveData(data: Auth?) {
        if (data != null) {
            loginViewModel.saveData(data.token, data.user).observe(this@LoginActivity) { result ->
                if (result) {
                    showToast("Welcome To My Application")

                    val intentToDashboard =
                        Intent(this@LoginActivity, DashboardActivity::class.java)
                    startActivity(intentToDashboard)
                    finish()
                } else {
                    showToast("Something went wrong, please login again")
                }
            }
        }
    }

    private fun handleFormInputs() {
        val emailTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmailFormInputs()
            }

            override fun afterTextChanged(s: Editable?) {
                validateEmailFormInputs()
            }
        }

        val passwordTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePasswordFormInputs()
            }

            override fun afterTextChanged(s: Editable?) {
                validatePasswordFormInputs()
            }
        }

        binding.edLoginEmail.addTextChangedListener(emailTextWatcher)
        binding.edLoginPassword.addTextChangedListener(passwordTextWatcher)

        binding.edLoginPassword.setOnKeyListener(View.OnKeyListener { view, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_UP) {
                val inputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

                return@OnKeyListener true
            }
            false
        })
    }

    private fun validateEmailFormInputs() {
        val email = binding.edLoginEmail.text.toString()
        val isEmailFormatCorrect = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if (!isEmailFormatCorrect) {
            binding.layoutEmail.apply {
                isErrorEnabled = true
                error = "Please check your email format"
            }
        } else {
            binding.layoutEmail.isErrorEnabled = false
        }

        isButtonRegisterEnabled(email.isNotEmpty() && isEmailFormatCorrect)
    }

    fun validatePasswordFormInputs() {
        val password = binding.edLoginPassword.text.toString()
        val isPasswordFormatCorrect = password.length >= 8

        if (!isPasswordFormatCorrect) {
            binding.layoutPassword.apply {
                isErrorEnabled = true
                error = "Password must be at least 8 characters"
            }
        } else {
            binding.layoutPassword.isErrorEnabled = false
        }

        isButtonRegisterEnabled(password.isNotEmpty() && isPasswordFormatCorrect)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun isButtonRegisterEnabled(isEnabled: Boolean) {
        binding.btnLogin.isEnabled = isEnabled
    }
}