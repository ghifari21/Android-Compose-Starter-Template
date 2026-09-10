package com.project.feat.home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.project.feat.home.presentation.DetailScreen
import com.project.feat.home.presentation.HomeScreen

fun NavGraphBuilder.homeGraph(navController: NavController) {
    composable<HomeRoute> {
        HomeScreen(
            navigateToDetail = { id, title ->
                navController.navigate(DetailRoute(id = id, title = title))
            }
        )
    }

    composable<DetailRoute> { backStackEntry ->
        val args = backStackEntry.toRoute<DetailRoute>()
        DetailScreen(
            id = args.id,
            title = args.title,
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
