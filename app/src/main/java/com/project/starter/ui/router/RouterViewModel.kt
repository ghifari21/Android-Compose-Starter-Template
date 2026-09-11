package com.project.starter.ui.router

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.repository.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class RouterState {
    object Loading : RouterState()
    object Authenticated : RouterState()
    object Unauthenticated : RouterState()
}

@HiltViewModel
class RouterViewModel @Inject constructor(
    sessionManager: SessionManager
) : ViewModel() {

    val routerState: StateFlow<RouterState> = sessionManager.isLoggedIn()
        .map { isLoggedIn ->
            if (isLoggedIn) RouterState.Authenticated else RouterState.Unauthenticated
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RouterState.Loading
        )
}
