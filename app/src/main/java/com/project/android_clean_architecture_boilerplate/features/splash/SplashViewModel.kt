package com.project.android_clean_architecture_boilerplate.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.core.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val authUseCase: AuthUseCase): ViewModel(){

    fun getToken() = authUseCase.getToken().asLiveData()
}