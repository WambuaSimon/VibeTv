package com.vibetv.presentation.movies.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.vibetv.common.utils.Resource
import com.vibetv.presentation.movies.MoviesScreen
import com.vibetv.presentation.movies.MoviesViewModel

const val movieNavigationRoute = "movies_route"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.movieScreen(
    onNavigateToMovieDetails: (Int) -> Unit
) {
    composable(route = movieNavigationRoute) {
        val moviesViewModel: MoviesViewModel = hiltViewModel()
        val genreState by moviesViewModel.genreState.collectAsStateWithLifecycle(Resource.Loading)
        MoviesScreen(
            genreState = genreState,
            allMoviesModel = moviesViewModel.allMoviesModel,
            onGenreClicked = {
                moviesViewModel.getAllMovies(it)
            },
            onDetailsClick = onNavigateToMovieDetails
        )
    }
}