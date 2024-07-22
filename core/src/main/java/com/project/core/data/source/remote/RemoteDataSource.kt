package com.project.core.data.source.remote

import com.project.core.data.source.remote.network.ApiResponse
import com.project.core.data.source.remote.network.ApiService
import com.project.core.data.source.remote.response.auth.LoginResponse
import com.project.core.data.source.remote.response.auth.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    // AUTH

    suspend fun registerUser(name: String, email: String, password: String): Flow<ApiResponse<Result<RegisterResponse>>> {
        return flow {
            try {
                val response = apiService.registerUser(name, email, password)

                if (response.isSuccess){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                Timber.tag("RemoteDataSource").e("registerUser: ${e.message}")
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun loginUser(email: String, password:String): Flow<ApiResponse<LoginResponse>>{
        return flow<ApiResponse<LoginResponse>> {
            try {
                val response = apiService.loginUser(email, password)

                if (response.loginResult?.userId != null){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                Timber.tag("RemoteDataSource").e("loginUser: ${e.message}")
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    // AUTH-END

}