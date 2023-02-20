package com.vibetv.presentation.movie_details.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.vibetv.presentation.movie_details.MovieDetailsScreen
import com.vibetv.presentation.movie_details.MovieDetailsViewModel

internal const val MovieDetailsRoutePattern = "details"

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.movieDetailsScreen(
    onNavigateUp: () -> Unit,
){
    composable(MovieDetailsRoutePattern){
        val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
        MovieDetailsScreen(
            onNavigateUp = onNavigateUp,
            model = movieDetailsViewModel.model
        )
    }
}