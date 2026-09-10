package com.project.data.source.remote.interceptor

import com.project.data.source.local.datastore.SessionManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // We use runBlocking here because Interceptor is synchronous
        // but DataStore is asynchronous (Flow).
        // Note: In production, consider reading the token synchronously 
        // if cached, to avoid blocking the network thread unnecessarily.
        val token = runBlocking { sessionManager.getToken().firstOrNull() }

        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}
