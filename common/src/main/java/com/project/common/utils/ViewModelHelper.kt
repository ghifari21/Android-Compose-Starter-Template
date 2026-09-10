package com.project.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.project.common.base.BaseViewModel
import com.project.common.base.UiState
import kotlinx.coroutines.flow.collectLatest

/**
 * Collects the UI state and one-time UI effects from the [BaseViewModel] in a lifecycle-aware manner.
 *
 * @param Event The type representing UI events.
 * @param StateData The type representing the UI state data.
 * @param Effect The type representing one-time UI effects.
 * @param onEffect A lambda expression invoked whenever a new UI effect is emitted.
 * @return A [State] object representing the current [UiState], which triggers recomposition upon change.
 */
@Composable
inline fun <reified Event, reified StateData, reified Effect> BaseViewModel<Event, StateData, Effect>.collectMvi(
    crossinline onEffect: (Effect) -> Unit
): State<UiState<StateData>> {
    val state = this.uiState.collectAsState()

    LaunchedEffect(Unit) {
        this@collectMvi.effect.collectLatest { effectValue ->
            onEffect(effectValue)
        }
    }

    return state
}