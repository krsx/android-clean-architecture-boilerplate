package com.project.core.di.retrofit

import com.project.core.data.source.local.LocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val localDataSource: LocalDataSource):
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val storedToken = runBlocking {
            localDataSource.getToken().first().toString()
        }
        Timber.tag("AuthInterceptor").d("Stored token: $storedToken")

        val request = chain.request().newBuilder()
        if (storedToken.isNotEmpty()){
            request.addHeader("Authorization", "Bearer $storedToken")
        }

        return chain.proceed(request.build())
    }
}