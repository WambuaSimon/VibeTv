package com.vibetv.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.vibetv.presentation.home.navigation.homeNavigationRoute
import com.vibetv.presentation.home.navigation.navigateToHomeGraph
import com.vibetv.presentation.movies.navigation.movieNavigationRoute
import com.vibetv.presentation.movies.navigation.navigateToMovieGraph
import com.vibetv.presentation.shows.navigation.navigateToShowsGraph
import com.vibetv.presentation.shows.navigation.showNavigationRoute


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberVibeAppState(
    navController: NavHostController = rememberAnimatedNavController(),
): VibeAppState {
    return remember(navController) {
        VibeAppState(navController)
    }
}


@Stable
class VibeAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            homeNavigationRoute -> TopLevelDestination.HOME
            movieNavigationRoute -> TopLevelDestination.MOVIES
            showNavigationRoute -> TopLevelDestination.SHOWS
           // favouritesNavigationRoute -> TopLevelDestination.FAVOURITES
            else -> null


        }

    var shouldShowSettingsDialog by mutableStateOf(false)
        private set

    fun setShowSettingsDialog(shouldShow: Boolean) {
        shouldShowSettingsDialog = shouldShow
    }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // re-selecting the same item
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.navigateToHomeGraph(topLevelNavOptions)
            TopLevelDestination.MOVIES -> navController.navigateToMovieGraph(topLevelNavOptions)
            TopLevelDestination.SHOWS -> navController.navigateToShowsGraph(topLevelNavOptions)
           // TopLevelDestination.FAVOURITES -> navController.navigateToFavourites(topLevelNavOptions)
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}

