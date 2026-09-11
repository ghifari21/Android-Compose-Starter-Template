package com.project.domain.repository

import kotlinx.coroutines.flow.Flow

interface SessionManager {
    fun getAccessToken(): Flow<String?>
    fun getRefreshToken(): Flow<String?>
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun clearSession()
    fun isLoggedIn(): Flow<Boolean>
}
