package com.project.core.utils.data_mapper

import com.project.core.data.source.remote.response.auth.LoginResponse
import com.project.core.domain.model.User
import com.project.core.utils.NO_DATA

object AuthDataMapper {
    fun mapLoginResponseToDomain(response: LoginResponse, email: String): User = User(
        name = response.loginResult?.name ?: NO_DATA,
        email = email
    )
}