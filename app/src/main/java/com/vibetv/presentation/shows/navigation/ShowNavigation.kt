package com.vibetv.presentation.shows.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.vibetv.presentation.shows.TvShowViewModel
import com.vibetv.presentation.shows.TvShowsScreen

const val showNavigationRoute = "shows_route"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.showsScreen(
    onNavigateToShowDetails: (Int) -> Unit
) {
    composable(route = showNavigationRoute) {
        val showViewModel: TvShowViewModel = hiltViewModel()
        TvShowsScreen(
            state = showViewModel.state.collectAsStateWithLifecycle().value,
            model = showViewModel.model,
            onNavigateToShowDetails = onNavigateToShowDetails,
            onMoreClicked = {_,_ ->}
        )
    }
}