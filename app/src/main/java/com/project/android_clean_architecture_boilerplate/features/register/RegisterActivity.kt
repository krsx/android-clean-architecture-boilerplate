package com.project.android_clean_architecture_boilerplate.features.register

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
import com.project.android_clean_architecture_boilerplate.databinding.ActivityRegisterBinding
import com.project.android_clean_architecture_boilerplate.features.login.LoginActivity
import com.project.core.data.source.Resource
import com.project.core.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val activityNameTag = "RegisterActivity"

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        isButtonRegisterEnabled(false)

        handleFormInputs()

        handleRegisterButton()

        handleLoginHereButton()
    }

    private fun handleLoginHereButton() {
        binding.tvToLogin.setOnClickListener {
            val intentToLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intentToLogin)
        }
    }

    private fun handleRegisterButton() {
        binding.btnRegister.setOnClickListener {
            val name = binding.edRegisName.text.toString()
            val email = binding.edRegisEmail.text.toString()
            val password = binding.edRegisPassword.text.toString()

            registerViewModel.registerUser(email, password, name).observe(this) { result ->
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

                        showToast("User Registered")
                        val intentToLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intentToLogin)
                        finish()
                    }
                }
            }
        }
    }

    private fun handleFormInputs() {
        val nameTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateNameFormInputs()
            }

            override fun afterTextChanged(s: Editable?) {
                validateNameFormInputs()
            }
        }

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

        val passwordConfirmTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePasswordConfirmFormInputs()
            }

            override fun afterTextChanged(s: Editable?) {
                validatePasswordConfirmFormInputs()
            }
        }

        binding.edRegisName.addTextChangedListener(nameTextWatcher)
        binding.edRegisEmail.addTextChangedListener(emailTextWatcher)
        binding.edRegisPassword.addTextChangedListener(passwordTextWatcher)
        binding.edRegisPasswordConfirm.addTextChangedListener(passwordConfirmTextWatcher)

        binding.edRegisPasswordConfirm.setOnKeyListener(View.OnKeyListener { view, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_UP) {
                val inputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

                return@OnKeyListener true
            }
            false
        })
    }

    private fun validateNameFormInputs() {
        val name = binding.edRegisName.text.toString()
        if (name.isEmpty()) {
            binding.layoutName.error = "Please fill this form"
        } else {
            binding.layoutName.error = ""
        }

        isButtonRegisterEnabled(name.isNotEmpty())
    }

    private fun validateEmailFormInputs() {
        val email = binding.edRegisEmail.text.toString()
        val isEmailFormatCorrect = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if (!isEmailFormatCorrect) {
            binding.layoutEmail.error = "Please check your email format"
        } else {
            binding.layoutEmail.error = ""
        }

        isButtonRegisterEnabled(email.isNotEmpty() && isEmailFormatCorrect)
    }

    fun validatePasswordFormInputs() {
        val password = binding.edRegisPassword.text.toString()
        val isPasswordFormatCorrect = password.length >= 8

        if (!isPasswordFormatCorrect) {
            binding.layoutPassword.error = "Password must be at least 8 characters"
        } else {
            binding.layoutPassword.error = ""
        }

        isButtonRegisterEnabled(password.isNotEmpty() && isPasswordFormatCorrect)
    }

    fun validatePasswordConfirmFormInputs() {
        val password = binding.edRegisPassword.text.toString()
        val passwordConfirm = binding.edRegisPasswordConfirm.text.toString()
        val isPasswordConfirmFormatCorrect = passwordConfirm.length >= 8

        if (!isPasswordConfirmFormatCorrect) {
            binding.layoutPasswordConfirm.error = "Password Confirm must be at least 8 characters"
        } else if (password != passwordConfirm) {
            binding.layoutPasswordConfirm.error = "Password and Password Confirm isn't same"
        } else {
            binding.layoutPasswordConfirm.error = ""
        }

        isButtonRegisterEnabled(passwordConfirm.isNotEmpty() && isPasswordConfirmFormatCorrect)
    }

    private fun setupActionBar() {
        supportActionBar?.hide()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun isButtonRegisterEnabled(isEnabled: Boolean) {
        binding.btnRegister.isEnabled = isEnabled
    }
}