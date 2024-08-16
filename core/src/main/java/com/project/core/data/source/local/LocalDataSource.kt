package com.project.core.data.source.local

import com.project.core.data.source.local.datastore.DatastoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val datastoreManager: DatastoreManager) {

    fun saveToken(token: String): Flow<Boolean> = datastoreManager.saveToken(token)

    fun getToken(): Flow<String> = datastoreManager.getToken()

    fun deleteToken(): Flow<Boolean> = datastoreManager.deleteToken()
}