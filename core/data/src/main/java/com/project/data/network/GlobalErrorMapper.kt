package com.project.data.network

import com.project.common.exception.AppException
import retrofit2.HttpException
import java.io.IOException

object GlobalErrorMapper {
    fun map(throwable: Throwable): AppException {
        return when (throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    401 -> AppException.UnauthorizedException("Session expired. Please log in again.")
                    500, 502, 503, 504 -> AppException.ServerException("Server error occurred. Please try again later.")
                    else -> AppException.ApiException(code = throwable.code(), message = "An unexpected network error occurred (Code: ${throwable.code()}).")
                }
            }
            is IOException -> {
                AppException.NetworkException("No internet connection or server is unreachable.")
            }
            is AppException -> throwable // Already mapped
            else -> AppException.UnknownException(throwable.localizedMessage ?: "An unknown error occurred.")
        }
    }
}
