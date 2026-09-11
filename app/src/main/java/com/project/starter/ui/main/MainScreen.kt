package com.project.starter.ui.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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
    windowSizeClass: WindowSizeClass,
    mainNavController: NavHostController = rememberNavController()
) {
    val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Only show nav if the current route is in the bottomNavItems
    val isNavVisible = bottomNavItems.any { item ->
        currentDestination?.hierarchy?.any { it.hasRoute(item.route) } == true
    }

    if (isCompact) {
        // Phone Layout: Bottom Navigation
        Scaffold(
            bottomBar = {
                if (isNavVisible) {
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
                                onClick = { navigateToTopLevel(mainNavController, item.routeObject) }
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
                homeGraph(mainNavController)
            }
        }
    } else {
        // Tablet/Foldable Layout: Navigation Rail
        Row(modifier = Modifier.fillMaxSize()) {
            if (isNavVisible) {
                NavigationRail {
                    bottomNavItems.forEach { item ->
                        val isSelected = currentDestination?.hierarchy?.any { it.hasRoute(item.route) } == true
                        NavigationRailItem(
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            label = { Text(item.title) },
                            selected = isSelected,
                            onClick = { navigateToTopLevel(mainNavController, item.routeObject) }
                        )
                    }
                }
            }
            BaseNavHost(
                modifier = Modifier.fillMaxSize(),
                navHostController = mainNavController,
                startDestination = HomeRoute
            ) {
                homeGraph(mainNavController)
            }
        }
    }
}

private fun navigateToTopLevel(navController: NavHostController, route: Any) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
