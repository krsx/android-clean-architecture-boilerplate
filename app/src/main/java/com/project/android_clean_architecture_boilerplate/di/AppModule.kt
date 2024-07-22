package com.project.android_clean_architecture_boilerplate.di

import com.project.core.domain.usecase.auth.AuthInteractor
import com.project.core.domain.usecase.auth.AuthUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideAuthUseCase(authInteractor: AuthInteractor): AuthUseCase
}