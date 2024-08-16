package com.project.core.utils.data_mapper

import com.project.core.data.source.remote.response.auth.LoginResponse
import com.project.core.domain.model.Auth
import com.project.core.domain.model.User
import com.project.core.utils.NO_DATA

object AuthDataMapper {
    fun mapLoginResponseToDomain(response: LoginResponse, email: String): Auth = Auth(
       user = User(
           name = response.loginResult?.name ?: NO_DATA,
           email = email
       ),
        token = response.loginResult?.token ?: NO_DATA
    )
}