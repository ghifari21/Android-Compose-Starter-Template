package com.project.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.common.exception.AppException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * A base class for ViewModels that follows the MVI (Model-View-Intent) pattern.
 *
 * It manages the UI state and one-time events (side effects) using Kotlin Coroutines and Flow.
 *
 * @param S The type representing the UI state.
 * @param E The type representing one-time events (e.g., navigation, showing a Snackbar).
 * @param initialState The initial state of the UI when the ViewModel is created.
 */
abstract class BaseViewModel<S : UiState, E>(initialState: S) : ViewModel() {

    private val _uiState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val uiState: StateFlow<S>
        get() = _uiState.asStateFlow()

    private val _uiEvent = Channel<E>()
    val uiEvent: Flow<E>
        get() = _uiEvent.receiveAsFlow()

    protected abstract fun updateLoading(isLoading: Boolean)

    protected abstract fun updateError(error: AppException?)

    protected fun updateState(reducer: S.() -> S) {
        _uiState.value = _uiState.value.reducer()
    }

    protected suspend fun sendEvent(event: E) {
        _uiEvent.send(event)
    }

    /**
     * Launches a coroutine in the [viewModelScope] and automatically manages loading and error states.
     *
     * @param block The suspendable block of code to execute.
     */
    protected fun safeLaunch(block: suspend () -> Unit) {
        viewModelScope.launch {
            updateLoading(true)
            updateError(null)

            try {
                block()
            } catch (e: Exception) {
                // Here we might need a way to convert generic Exception to AppException
                // For now, let's assume we handle it or use a default mapper if available
                if (e is AppException) {
                    updateError(e)
                } else {
                    updateError(AppException.UnknownException(cause = e))
                }
            } finally {
                updateLoading(false)
            }
        }
    }

    protected fun launchAsync(
        onLoading: (Boolean) -> Unit,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            onLoading(true)

            try {
                block()
            } finally {
                onLoading(false)
            }
        }
    }
}
