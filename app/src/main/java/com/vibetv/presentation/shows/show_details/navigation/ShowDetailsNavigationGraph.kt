package com.vibetv.presentation.shows.show_details.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument

private const val ShowDetailsRouteGraph = "info_route/{infoId}"
internal fun NavController.navigateToShowDetails(id: Int) =
    navigate(
        ShowDetailsRouteGraph.replace("{infoId}", Uri.encode(id.toString()))
    )

internal fun NavGraphBuilder.showsDetailsGraph(
    navController: NavController,

){
    navigation(
        startDestination = ShowDetailsRoutePattern,
        route = ShowDetailsRouteGraph,
        arguments = listOf(navArgument("infoId"){type = NavType.IntType})
    ){
        showDetailsScreen(
            onNavigateUp = navController::navigateUp,

        )
    }
}
