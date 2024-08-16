package com.project.android_clean_architecture_boilerplate.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.core.domain.model.User
import com.project.core.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    fun loginUser(email: String, password: String) =
        authUseCase.loginUser(email, password).asLiveData()

    fun saveToken(token: String) = authUseCase.saveToken(token)

    fun saveUserData(user: User) = authUseCase.saveUserData(user)

    fun saveData(token: String, user: User) =
        combine(saveToken(token), saveUserData(user)) { tokenResult, userResult ->
            tokenResult && userResult
        }.asLiveData()
}