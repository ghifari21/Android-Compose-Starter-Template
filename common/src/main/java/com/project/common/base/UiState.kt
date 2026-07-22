package com.project.common.base

import com.project.common.exception.AppException

/**
 * Interface representing the basic requirements for a UI state.
 */
interface UiState {
    val isLoading: Boolean
    val error: AppException?
}
