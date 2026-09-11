package com.project.starter.ui.router

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.starter.ui.auth.AuthScreen
import com.project.starter.ui.main.MainScreen

@Composable
fun AppRouter(
    viewModel: RouterViewModel = hiltViewModel()
) {
    val routerState by viewModel.routerState.collectAsStateWithLifecycle()

    when (routerState) {
        is RouterState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is RouterState.Authenticated -> {
            MainScreen()
        }
        is RouterState.Unauthenticated -> {
            AuthScreen()
        }
    }
}
