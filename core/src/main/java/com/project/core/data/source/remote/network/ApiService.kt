package com.project.core.data.source.remote.network

import com.project.core.data.source.remote.response.auth.LoginResponse
import com.project.core.data.source.remote.response.auth.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    // AUTH

    @POST("register")
    @FormUrlEncoded
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ):Result<RegisterResponse>

    @POST("login")
    @FormUrlEncoded
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ):LoginResponse

    // AUTH-END

    // STORY


    // STORY-END
}