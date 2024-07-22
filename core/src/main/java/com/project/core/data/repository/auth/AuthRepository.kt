package com.project.core.data.repository.auth

import com.project.core.data.source.Resource
import com.project.core.data.source.remote.RemoteDataSource
import com.project.core.data.source.remote.network.ApiResponse
import com.project.core.domain.model.User
import com.project.core.domain.repository.auth.IAuthRepository
import com.project.core.utils.data_mapper.AuthDataMapper
import com.project.core.utils.no_data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val remoteDataSource: RemoteDataSource): IAuthRepository{

    override fun registerUser(email: String, password: String, name: String): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())

            when(val response = remoteDataSource.registerUser(name, email, password).first()){
                is ApiResponse.Empty ->{
                    emit(Resource.Message(no_data))
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

    override fun loginUser(email: String, password: String): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading())

            when(val response = remoteDataSource.loginUser(email, password).first()){
                is ApiResponse.Empty -> {
                    emit(Resource.Message(no_data))
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

}