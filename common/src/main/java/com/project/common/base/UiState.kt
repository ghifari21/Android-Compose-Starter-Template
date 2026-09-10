package com.project.common.base

import com.project.common.exception.AppException

/**
 * A generic UI state wrapper that automatically handles loading and error states.
 */
data class UiState<T>(
    val data: T,
    val isLoading: Boolean = false,
    val error: AppException? = null
)
