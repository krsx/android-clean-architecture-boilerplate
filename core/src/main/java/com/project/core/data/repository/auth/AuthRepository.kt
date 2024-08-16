package com.project.core.data.repository.auth

import com.project.core.data.source.Resource
import com.project.core.data.source.local.LocalDataSource
import com.project.core.data.source.remote.RemoteDataSource
import com.project.core.data.source.remote.network.ApiResponse
import com.project.core.domain.model.Auth
import com.project.core.domain.model.User
import com.project.core.domain.repository.auth.IAuthRepository
import com.project.core.utils.data_mapper.AuthDataMapper
import com.project.core.utils.NO_DATA
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource): IAuthRepository{

    override fun registerUser(email: String, password: String, name: String): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())

            when(val response = remoteDataSource.registerUser(name, email, password).first()){
                is ApiResponse.Empty ->{
                    emit(Resource.Message(NO_DATA))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(response.errorMessage))
                }
                is ApiResponse.Success -> {
                    emit(Resource.Success("User created"))
                }
            }
        }
    }

    override fun loginUser(email: String, password: String): Flow<Resource<Auth>> {
        return flow {
            emit(Resource.Loading())

            when(val response = remoteDataSource.loginUser(email, password).first()){
                is ApiResponse.Empty -> {
                    emit(Resource.Message(NO_DATA))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(response.errorMessage))
                }
                is ApiResponse.Success ->{
                    val user = AuthDataMapper.mapLoginResponseToDomain(response.data, email)
                    emit(Resource.Success(user))
                }
            }
        }
    }

    override fun saveToken(token: String): Flow<Boolean> {
        return localDataSource.saveToken(token)
    }

    override fun getToken(): Flow<String> {
        return localDataSource.getToken()
    }

    override fun deleteToken(): Flow<Boolean> {
        return localDataSource.deleteToken()
    }
}