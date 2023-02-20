package com.vibetv.presentation.movie_details.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument

private const val MovieDetailsRouteGraph = "details_route/{id}"
internal fun NavController.navigateToMovieDetails(id: Int) =
    navigate(
        MovieDetailsRouteGraph.replace("{id}", Uri.encode(id.toString()))
    )

internal fun NavGraphBuilder.movieDetailsGraph(
    navController: NavController
) {
    navigation(
        startDestination = MovieDetailsRoutePattern,
        route = MovieDetailsRouteGraph,
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ){
        movieDetailsScreen(
            onNavigateUp = navController::navigateUp
        )
    }
}