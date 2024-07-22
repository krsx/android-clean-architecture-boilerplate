package com.project.core.utils.data_mapper

import com.project.core.data.source.remote.response.auth.LoginResponse
import com.project.core.domain.model.User
import com.project.core.utils.no_data

object AuthDataMapper {
    fun mapLoginResponseToDomain(response: LoginResponse, email: String): User = User(
        name = response.loginResult?.name ?: no_data,
        email = email
    )
}