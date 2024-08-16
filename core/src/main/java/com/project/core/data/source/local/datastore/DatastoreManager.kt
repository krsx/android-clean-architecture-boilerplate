package com.project.core.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.project.core.utils.STORED_TOKEN
import com.project.core.utils.NO_DATA
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatastoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    fun saveToken(token: String): Flow<Boolean>{
        return flow {
            try {
                dataStore.edit {preferences ->
                    preferences[TOKEN_KEY_PREF] = token
                }
                emit(true)
            }catch (e:Exception){
                Timber.tag("DatastoreManager").e("saveToken: ${e.message}")
                emit(false)
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getToken(): Flow<String>{
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY_PREF] ?: NO_DATA
        }
    }

    fun deleteToken(): Flow<Boolean>{
        return flow<Boolean> {
            try {
               dataStore.edit {preferences ->
                   preferences.remove(TOKEN_KEY_PREF)
               }

                emit(true)
            }catch (e: Exception){
                Timber.tag("DatastoreManager").e("deleteToken: ${e.message}")
                emit(false)
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object{
        private val TOKEN_KEY_PREF = stringPreferencesKey(STORED_TOKEN)
    }
}