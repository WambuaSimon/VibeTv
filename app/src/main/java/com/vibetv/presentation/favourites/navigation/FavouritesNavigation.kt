package com.vibetv.presentation.favourites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.vibetv.presentation.favourites.FavouritesScreen

const val favouritesNavigationRoute = "favourites_route"

fun NavController.navigateToFavourites(navOptions: NavOptions? = null) {
    this.navigate(favouritesNavigationRoute, navOptions)
}

fun NavGraphBuilder.favouritesScreen() {
    composable(route = favouritesNavigationRoute) {
        FavouritesScreen()
    }
}