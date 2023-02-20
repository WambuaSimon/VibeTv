package com.vibetv.presentation.shows.show_details.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.vibetv.presentation.shows.show_details.ShowDetailsViewModel
import com.vibetv.presentation.shows.show_details.ShowsDetailsScreen

internal const val ShowDetailsRoutePattern = "info"

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.showDetailsScreen(
    onNavigateUp: () -> Unit,
) {
    composable(ShowDetailsRoutePattern) {
        val showDetailsViewModel: ShowDetailsViewModel = hiltViewModel()
        ShowsDetailsScreen(
            onNavigateUp = onNavigateUp,
            model = showDetailsViewModel.model,
            episodeModel = showDetailsViewModel.episodeModel,
            seasonDetailsModel = showDetailsViewModel.seasonsModel,
            onClickSeason = {
                showDetailsViewModel.getSeasonDetails(it)
            },
            onClickEpisode = {season, episode ->
                showDetailsViewModel.getEpisode(season,episode)
            }


        )
    }
}