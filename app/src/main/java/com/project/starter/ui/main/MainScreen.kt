package com.project.starter.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.feat.home.presentation.navigation.HomeRoute
import com.project.feat.home.presentation.navigation.homeGraph
import com.project.navigation.BaseNavHost
import kotlin.reflect.KClass

data class BottomNavItem(
    val title: String,
    val route: KClass<*>,
    val routeObject: Any,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        title = "Home",
        route = HomeRoute::class,
        routeObject = HomeRoute,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    // Placeholder for another tab (e.g., Profile)
    // BottomNavItem("Profile", ProfileRoute::class, ProfileRoute, Icons.Filled.Person, Icons.Outlined.Person)
)

@Composable
fun MainScreen(
    mainNavController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            // Only show bottom bar if the current route is in the bottomNavItems
            val isBottomBarVisible = bottomNavItems.any { item ->
                currentDestination?.hierarchy?.any { it.hasRoute(item.route) } == true
            }

            if (isBottomBarVisible) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val isSelected = currentDestination?.hierarchy?.any { it.hasRoute(item.route) } == true
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            label = { Text(item.title) },
                            selected = isSelected,
                            onClick = {
                                mainNavController.navigate(item.routeObject) {
                                    popUpTo(mainNavController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        BaseNavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            navHostController = mainNavController,
            startDestination = HomeRoute
        ) {
            // Register graphs that belong inside the Main bottom navigation skeleton
            homeGraph(mainNavController)
        }
    }
}
