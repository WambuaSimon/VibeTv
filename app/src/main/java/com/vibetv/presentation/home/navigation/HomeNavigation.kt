package com.vibetv.presentation.home.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.navigation.animation.composable
import com.vibetv.presentation.home.HomeRoute
import com.vibetv.presentation.home.HomeViewModel

const val homeNavigationRoute = "home_route"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeScreen(
    onNavigateToMovieDetails: (Int) -> Unit
) {
    composable(route = homeNavigationRoute,
        ) {
        val homeViewModel: HomeViewModel = hiltViewModel()
        val nowPlaying = homeViewModel.getNowPlaying.collectAsLazyPagingItems()
        HomeRoute(
            navigateToMovieDetails = onNavigateToMovieDetails,
            model = homeViewModel.model,
            nowPlaying = nowPlaying
        )
    }

}

