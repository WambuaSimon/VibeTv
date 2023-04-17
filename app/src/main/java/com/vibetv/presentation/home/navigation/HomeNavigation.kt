package com.vibetv.presentation.home.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.vibetv.presentation.home.HomeScreen
import com.vibetv.presentation.home.HomeViewModel

const val homeNavigationRoute = "home_route"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeScreen(
    onNavigateToMovieDetails: (Int) -> Unit,
    onNavigateToMovieGrid: () -> Unit
) {
    composable(route = homeNavigationRoute,
        ) {
        val homeViewModel: HomeViewModel = hiltViewModel()
        HomeScreen(
            navigateToMovieDetails = onNavigateToMovieDetails,
            model = homeViewModel.model,
            navigateToMovieGrid = onNavigateToMovieGrid
        )
    }

}

