package com.project.data.network

import com.project.data.local.EncryptedSessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider

class TokenAuthenticator @Inject constructor(
    // Use Provider to avoid circular dependency if SessionManager/Authenticator depend on Retrofit
    private val sessionManagerProvider: Provider<EncryptedSessionManager>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val sessionManager = sessionManagerProvider.get()
        val currentToken = sessionManager.getAccessTokenSync()

        synchronized(this) {
            val newToken = sessionManager.getAccessTokenSync()
            
            // If the token has changed since the request was made, use the new token.
            if (currentToken != newToken) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $newToken")
                    .build()
            }

            // Otherwise, attempt to refresh the token.
            val refreshToken = sessionManager.getRefreshTokenSync()
            if (refreshToken != null) {
                // TODO: Make synchronous network call to refresh token endpoint here
                // val newTokens = tokenApiService.refreshToken(refreshToken).execute().body()
                // if (newTokens != null) {
                //     runBlocking { sessionManager.saveTokens(newTokens.accessToken, newTokens.refreshToken) }
                //     return response.request.newBuilder()
                //         .header("Authorization", "Bearer ${newTokens.accessToken}")
                //         .build()
                // }
            }

            // If refresh fails or no refresh token, clear session and return null to stop retrying.
            runBlocking { sessionManager.clearSession() }
            return null
        }
    }
}
