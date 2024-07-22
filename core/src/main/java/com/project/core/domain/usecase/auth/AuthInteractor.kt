package com.project.core.domain.usecase.auth

import com.project.core.data.repository.auth.AuthRepository
import com.project.core.data.source.Resource
import com.project.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthInteractor @Inject constructor(private val authRepository: AuthRepository): AuthUseCase{
    override fun registerUser(email: String, password: String, name: String): Flow<Resource<String>> {
        return authRepository.registerUser(email, password, name)
        }

    override fun loginUser(email: String, password: String): Flow<Resource<User>> {
        return authRepository.loginUser(email, password)
    }
}