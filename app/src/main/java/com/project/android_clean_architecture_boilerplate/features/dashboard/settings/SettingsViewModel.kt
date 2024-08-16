package com.project.android_clean_architecture_boilerplate.features.dashboard.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.core.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val authUseCase: AuthUseCase): ViewModel() {

    fun deleteToken() = authUseCase.deleteToken().asLiveData()

    fun getToken() = authUseCase.getToken().asLiveData()
}