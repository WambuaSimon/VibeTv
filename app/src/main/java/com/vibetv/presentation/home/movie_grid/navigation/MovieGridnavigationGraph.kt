package com.vibetv.presentation.home.movie_grid.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.vibetv.presentation.movie_details.navigation.navigateToMovieDetails

private const val MovieGridRouteGraph = "grid_route"

internal fun NavController.navigateToMovieGrid() = navigate(MovieGridRouteGraph)
internal fun NavGraphBuilder.movieGridGraph(
    navController: NavController
) {
    navigation(
        startDestination = MovieGridRoutePattern,
        route = MovieGridRouteGraph
    ) {
        movieGridScreen(
            onNavigateUp = navController::navigateUp,
            onMovieDetailsClick = {
                navController.navigateToMovieDetails(it)
            }
        )
    }
}