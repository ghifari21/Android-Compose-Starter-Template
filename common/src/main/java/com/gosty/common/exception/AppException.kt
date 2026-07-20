package com.gosty.common.exception

/**
 * Base class for all application-specific exceptions.
 */
sealed class AppException(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {

    /**
     * Exception thrown when there is a network-related issue (e.g., no internet connection).
     */
    class NetworkException(
        message: String? = "No internet connection",
        cause: Throwable? = null
    ) : AppException(message, cause)

    /**
     * Exception thrown when the server returns a 5xx error.
     */
    class ServerException(
        message: String? = "Server error occurred",
        cause: Throwable? = null
    ) : AppException(message, cause)

    /**
     * Exception thrown for API-specific errors (typically 4xx, except 401).
     *
     * @property code The error code returned by the API.
     */
    class ApiException(
        val code: Int,
        message: String? = "API error occurred",
        cause: Throwable? = null
    ) : AppException(message, cause)

    /**
     * Exception thrown when the user is unauthorized (HTTP 401).
     */
    class UnauthorizedException(
        message: String? = "Unauthorized access",
        cause: Throwable? = null
    ) : AppException(message, cause)

    /**
     * Exception thrown for local storage issues (e.g., database or DataStore errors).
     */
    class LocalStorageException(
        message: String? = "Local storage error occurred",
        cause: Throwable? = null
    ) : AppException(message, cause)

    /**
     * Exception thrown for unknown or unexpected errors.
     */
    class UnknownException(
        message: String? = "Unknown error occurred",
        cause: Throwable? = null
    ) : AppException(message, cause)
}
