package com.vibetv.presentation.shows.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.vibetv.presentation.shows.show_details.navigation.navigateToShowDetails
import com.vibetv.presentation.shows.show_details.navigation.showsDetailsGraph

const val showsNavigationRoutePattern = "shows_graph"

fun NavController.navigateToShowsGraph(navOptions: NavOptions? = null) {
    this.navigate(showNavigationRoute, navOptions)
}

fun NavGraphBuilder.showsGraph(
    navController: NavController
) {
    navigation(
        startDestination = showNavigationRoute,
        route = showsNavigationRoutePattern
    ) {
        showsScreen(
            onNavigateToShowDetails = {
                navController.navigateToShowDetails(it)
            }
        )
        showsDetailsGraph(navController)
    }
}