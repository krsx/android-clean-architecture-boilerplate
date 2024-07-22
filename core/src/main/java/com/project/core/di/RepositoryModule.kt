package com.project.core.di

import com.project.core.data.repository.auth.AuthRepository
import com.project.core.domain.repository.auth.IAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(authRepository: AuthRepository): IAuthRepository
}