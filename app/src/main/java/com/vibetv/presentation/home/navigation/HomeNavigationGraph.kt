package com.vibetv.presentation.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.vibetv.presentation.home.movie_grid.navigation.movieGridScreen
import com.vibetv.presentation.home.movie_grid.navigation.navigateToMovieGrid
import com.vibetv.presentation.movie_details.navigation.movieDetailsGraph
import com.vibetv.presentation.movie_details.navigation.navigateToMovieDetails


const val homeNavigationGraphRoutePattern = "home_graph"

fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeGraph(
    navController: NavController,
) {
    navigation(
        startDestination = homeNavigationRoute,
        route = homeNavigationGraphRoutePattern,
    ) {
        homeScreen(
            onNavigateToMovieDetails = {
                navController.navigateToMovieDetails(it)
            },
            onNavigateToMovieGrid = { title, time ->
                navController.navigateToMovieGrid(
                    title = title,
                    time = time.orEmpty()
                )
            }
        )
        movieDetailsGraph(navController)
        movieGridScreen(
            onNavigateUp = navController::navigateUp,
            onMovieDetailsClick = {}
        )
    }
}