package com.project.core.domain.usecase.auth

import com.project.core.data.source.Resource
import com.project.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    fun registerUser(
        email: String,
        password: String,
        name: String
    ): Flow<Resource<String>>

    fun loginUser(
        email: String,
        password: String
    ): Flow<Resource<User>>
}