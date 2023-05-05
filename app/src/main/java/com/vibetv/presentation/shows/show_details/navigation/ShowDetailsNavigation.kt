package com.vibetv.presentation.shows.show_details.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.vibetv.presentation.shows.show_details.ShowDetailsViewModel
import com.vibetv.presentation.shows.show_details.ShowsDetailsScreen

internal const val ShowDetailsRoutePattern = "info/{infoId}"

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.showDetailsScreen(
    onNavigateUp: () -> Unit,
) {
    composable(ShowDetailsRoutePattern,
        arguments = listOf(
            navArgument("infoId") { type = NavType.IntType },
        )) {
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

fun NavController.navigateToShowDetails(
    infoId:Int,
) = navigate(ShowDetailsRoutePattern.replace("{infoId}",infoId.toString()))