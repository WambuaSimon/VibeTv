package com.vibetv.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.vibetv.presentation.home.navigation.homeGraph
import com.vibetv.presentation.home.navigation.homeNavigationGraphRoutePattern
import com.vibetv.presentation.movies.navigation.movieGraph
import com.vibetv.presentation.shows.navigation.showsGraph

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VibeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationGraphRoutePattern

) {

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(500))
        },

        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )

        },

        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )

        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )

        }

    ) {
        movieGraph(navController)
        showsGraph(navController)
        //favouritesScreen()
        homeGraph(navController)
    }
}

private fun mainScreens(initial: NavBackStackEntry, target: NavBackStackEntry): Boolean {
    return initial.destination.hierarchy.last { it.route != null } !=
            target.destination.hierarchy.last { it.route != null }

}