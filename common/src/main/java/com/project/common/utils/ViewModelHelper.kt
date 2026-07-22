package com.project.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.project.common.base.BaseViewModel
import com.project.common.base.UiState
import kotlinx.coroutines.flow.collectLatest

/**
 * Collects the UI state and one-time UI events from the [BaseViewModel] in a lifecycle-aware manner.
 *
 * @param S The type representing the UI state.
 * @param E The type representing one-time UI events.
 * @param onEvent A lambda expression invoked whenever a new UI event is emitted.
 * @return A [State] object representing the current UI state, which triggers recomposition upon change.
 */
@Composable
inline fun <reified S : UiState, reified E> BaseViewModel<S, E>.collectMvi(
    crossinline onEvent: (E) -> Unit
): State<S> {
    val state = this.uiState.collectAsState()

    LaunchedEffect(Unit) {
        this@collectMvi.uiEvent.collectLatest { event ->
            onEvent(event)
        }
    }

    return state
}