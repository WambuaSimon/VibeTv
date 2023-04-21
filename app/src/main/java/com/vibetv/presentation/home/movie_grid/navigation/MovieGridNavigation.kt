package com.vibetv.presentation.home.movie_grid.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.vibetv.presentation.home.movie_grid.MovieGridScreen
import com.vibetv.presentation.home.movie_grid.MovieGridViewModel

internal const val MovieGridRoutePattern = "grid/{title}"

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.movieGridScreen(
    onNavigateUp: () -> Unit,
    onMovieDetailsClick: (Int) -> Unit
) {
    composable(MovieGridRoutePattern,
        arguments = listOf(
            navArgument("title") { type = NavType.StringType }
        )
    ) {
        val viewModel: MovieGridViewModel = hiltViewModel()
        MovieGridScreen(
            onNavigateUp = onNavigateUp,
            nowPlaying = emptyList(),
            onMovieDetailsClick = onMovieDetailsClick,
            model = viewModel.model

        )
    }
}

fun NavController.navigateToMovieGrid(
    title: String
) = navigate(MovieGridRoutePattern.replace("{title}", title))