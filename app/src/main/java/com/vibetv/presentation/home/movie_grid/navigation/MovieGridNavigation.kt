package com.vibetv.presentation.home.movie_grid.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.vibetv.presentation.home.HomeViewModel
import com.vibetv.presentation.home.movie_grid.MovieGridScreen

internal const val MovieGridRoutePattern = "grid"

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.movieGridScreen(
    onNavigateUp: () -> Unit,
    onMovieDetailsClick: (Int) -> Unit
) {

    composable(MovieGridRoutePattern) {
        val homeViewModel: HomeViewModel = hiltViewModel()
        // val nowPlaying = homeViewModel.playing.collectAsLazyPagingItems()
        MovieGridScreen(
            onNavigateUp = onNavigateUp,
            nowPlaying = emptyList(),
            onMovieDetailsClick = onMovieDetailsClick


        )
    }
}