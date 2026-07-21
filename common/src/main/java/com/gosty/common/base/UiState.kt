package com.gosty.common.base

import com.gosty.common.exception.AppException

/**
 * Interface representing the basic requirements for a UI state.
 */
interface UiState {
    val isLoading: Boolean
    val error: AppException?
}
