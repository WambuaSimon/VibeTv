package com.vibetv.presentation.movies.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.vibetv.presentation.movie_details.navigation.movieDetailsGraph
import com.vibetv.presentation.movie_details.navigation.navigateToMovieDetails

const val movieNavigationGraphRoutePattern = "movie_graph"

    fun NavController.navigateToMovieGraph(navOptions: NavOptions? = null) {
        this.navigate(movieNavigationRoute, navOptions)
    }

fun NavGraphBuilder.movieGraph(
    navController: NavController
){
    navigation(
        startDestination = movieNavigationRoute,
        route = movieNavigationGraphRoutePattern
    ){
        movieScreen(
            onNavigateToMovieDetails = {
                navController.navigateToMovieDetails(it)
            }
        )
        movieDetailsGraph(navController)
    }
}
