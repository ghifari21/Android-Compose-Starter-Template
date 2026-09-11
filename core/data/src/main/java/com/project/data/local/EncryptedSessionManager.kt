package com.project.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.project.domain.repository.SessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class EncryptedSessionManager(context: Context) : SessionManager {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secret_shared_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // Using MutableStateFlow to make SharedPreferences reactive
    private val _accessTokenFlow = MutableStateFlow(sharedPreferences.getString(KEY_ACCESS_TOKEN, null))
    private val _refreshTokenFlow = MutableStateFlow(sharedPreferences.getString(KEY_REFRESH_TOKEN, null))

    override fun getAccessToken(): Flow<String?> = _accessTokenFlow
    override fun getRefreshToken(): Flow<String?> = _refreshTokenFlow

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.edit()
            .putString(KEY_ACCESS_TOKEN, accessToken)
            .putString(KEY_REFRESH_TOKEN, refreshToken)
            .apply()
        
        _accessTokenFlow.value = accessToken
        _refreshTokenFlow.value = refreshToken
    }

    override suspend fun clearSession() {
        sharedPreferences.edit().clear().apply()
        _accessTokenFlow.value = null
        _refreshTokenFlow.value = null
    }

    override fun isLoggedIn(): Flow<Boolean> = _accessTokenFlow.map { !it.isNullOrBlank() }

    fun getAccessTokenSync(): String? = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    fun getRefreshTokenSync(): String? = sharedPreferences.getString(KEY_REFRESH_TOKEN, null)

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }
}
