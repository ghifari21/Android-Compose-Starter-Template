package com.project.data.utils

import com.project.common.exception.AppException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection

/**
 * Extension function to handle Retrofit [Response] and extract data or throw [AppException].
 *
 * Use this inside safeCall in your repositories.
 */
fun <T> Response<T>.handleResponse(): T {
    if (isSuccessful) {
        return body() ?: throw Exception("Response body is null")
    } else {
        throw handleApiError()
    }
}

/**
 * Internal extension function to map a Retrofit [Response] to an [AppException].
 */
private fun <T> Response<T>.handleApiError(): AppException {
    return when (code()) {
        HttpURLConnection.HTTP_UNAUTHORIZED -> AppException.UnauthorizedException(
            message = "Session expired. Please login again.",
            cause = null
        )
        in 500..599 -> AppException.ServerException(
            message = "Server error: ${code()}",
            cause = null
        )
        else -> AppException.ApiException(
            code = code(),
            message = message(),
            cause = null
        )
    }
}

/**
 * Extension function to map a [Throwable] to an [AppException].
 *
 * It identifies network issues (like [IOException]) and unknown errors.
 */
fun Throwable.toAppException(): AppException {
    return when (this) {
        is AppException -> this
        is IOException -> AppException.NetworkException(
            message = "No internet connection. Please check your network.",
            cause = this
        )
        else -> AppException.UnknownException(
            message = this.message ?: "An unexpected error occurred",
            cause = this
        )
    }
}
