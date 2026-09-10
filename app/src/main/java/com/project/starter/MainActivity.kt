package com.project.starter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.project.common.ui.theme.AppTheme
import com.project.feat.home.presentation.navigation.HomeRoute
import com.project.feat.home.presentation.navigation.homeGraph
import com.project.navigation.BaseNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BaseNavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        navHostController = navController,
                        startDestination = HomeRoute
                    ) {
                        homeGraph(navController)
                    }
                }
            }
        }
    }
}
