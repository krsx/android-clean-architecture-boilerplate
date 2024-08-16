package com.project.core.domain.repository.auth

import com.project.core.data.source.Resource
import com.project.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {

    fun registerUser(
        email: String,
        password: String,
        name: String
    ): Flow<Resource<String>>

    fun loginUser(
        email: String,
        password: String
    ): Flow<Resource<User>>

    fun saveToken(token: String): Flow<Boolean>

    fun getToken(): Flow<String>

    fun deleteToken(): Flow<Boolean>
}