package com.project.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.common.exception.AppException
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * A base class for ViewModels that follows the MVI (Model-View-Intent) pattern.
 *
 * It manages the UI state and one-time events (side effects) using Kotlin Coroutines and Flow.
 *
 * @param Event The type representing the UI intent/event triggered from UI.
 * @param State The type representing the UI state data.
 * @param Effect The type representing one-time events (e.g., navigation, showing a Snackbar).
 * @param initialStateData The initial data state of the UI when the ViewModel is created.
 */
abstract class BaseViewModel<Event, State, Effect>(initialStateData: State) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState(data = initialStateData))
    val uiState: StateFlow<UiState<State>> = _uiState.asStateFlow()

    private val _effect = Channel<Effect>()
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    private val _event = MutableSharedFlow<Event>()

    /**
     * Active jobs mapped by their keys. Used for single-flight requests.
     * Prevents duplicate requests from running concurrently.
     */
    private val activeRequests = mutableMapOf<String, Job>()

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            _event.collect { event ->
                handleEvent(event)
            }
        }
    }

    /**
     * Handle UI events
     */
    protected abstract fun handleEvent(event: Event)

    /**
     * Set new Event from UI to trigger ViewModel
     */
    fun setEvent(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    /**
     * Set new Ui State Data
     */
    protected fun updateState(reducer: State.() -> State) {
        _uiState.update { it.copy(data = it.data.reducer()) }
    }

    /**
     * Set new Effect to send to UI
     */
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    /**
     * Send effect directly
     */
    protected suspend fun sendEffect(effectValue: Effect) {
        _effect.send(effectValue)
    }

    private fun updateLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    private fun updateError(error: AppException?) {
        _uiState.update { it.copy(error = error) }
    }

    /**
     * Cancels all currently running jobs in [activeRequests] and clears the map.
     * Can be called from the UI when a composable leaves the composition (e.g., navigating away).
     */
    fun cancelLoads() {
        val jobs = activeRequests.values.toList()
        activeRequests.clear()
        jobs.forEach { it.cancel() }
    }

    /**
     * Launches a coroutine in the [viewModelScope] and automatically manages loading and error states.
     *
     * @param key Optional key for single-flight mechanism. If a new request with the same key arrives, the previous one is cancelled.
     * @param showLoading Whether to update the loading state automatically.
     * @param block The suspendable block of code to execute.
     */
    protected fun safeLaunch(
        key: String? = null,
        showLoading: Boolean = true,
        block: suspend () -> Unit
    ) {
        // Single-flight: cancel previous request with the same key to prevent stacking.
        if (key != null) activeRequests.remove(key)?.cancel()

        val job = viewModelScope.launch {
            if (showLoading) updateLoading(true)
            updateError(null)

            try {
                block()
            } catch (e: Exception) {
                if (e is AppException) {
                    updateError(e)
                } else {
                    updateError(AppException.UnknownException(cause = e))
                }
            } finally {
                if (showLoading) updateLoading(false)
            }
        }

        if (key != null) {
            activeRequests[key] = job
            job.invokeOnCompletion { 
                if (activeRequests[key] === job) activeRequests.remove(key) 
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
