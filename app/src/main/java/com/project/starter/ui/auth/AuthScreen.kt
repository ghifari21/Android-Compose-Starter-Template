package com.project.starter.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.project.domain.repository.SessionManager
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch

@Composable
fun AuthScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    // In a real app, you would use a ViewModel and inject SessionManager there.
    // This is just a quick shortcut for the dummy AuthScreen.
    val sessionManager = EntryPointAccessors.fromApplication(
        context.applicationContext,
        AuthEntryPoint::class.java
    ).sessionManager()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login Screen", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                sessionManager.saveTokens("dummy_access", "dummy_refresh")
            }
        }) {
            Text("Simulate Login")
        }
    }
}
