package com.vibetv.presentation.movies.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.vibetv.presentation.movies.MoviesScreen
import com.vibetv.presentation.movies.MoviesViewModel

const val movieNavigationRoute = "movies_route"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.movieScreen(
    onNavigateToMovieDetails: (Int) -> Unit
) {
    composable(route = movieNavigationRoute) {
        val moviesViewModel: MoviesViewModel = hiltViewModel()
        MoviesScreen(
            genreModel = moviesViewModel.model,
            allMoviesModel = moviesViewModel.allMoviesModel,
            onGenreClicked = {
                moviesViewModel.getAllMovies(it)
            },
            onDetailsClick = onNavigateToMovieDetails
        )
    }
}