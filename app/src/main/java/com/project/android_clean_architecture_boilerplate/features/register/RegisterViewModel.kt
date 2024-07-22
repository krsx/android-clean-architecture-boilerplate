package com.project.android_clean_architecture_boilerplate.features.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.core.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authUseCase: AuthUseCase): ViewModel() {
    fun registerUser(email: String, password: String, name: String) = authUseCase.registerUser(email, password, name).asLiveData()
}