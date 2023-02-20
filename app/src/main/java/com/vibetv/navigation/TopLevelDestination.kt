package com.vibetv.navigation

import com.vibetv.R
import com.vibetv.designSystem.theme.Icon
import com.vibetv.designSystem.theme.Icon.DrawableResourceIcon
import com.vibetv.designSystem.theme.VibeIcons

enum class TopLevelDestination(
    val icon: Icon,
    val iconTextId: Int,
    val titleTextId: Int
) {
    HOME(
        icon = DrawableResourceIcon(VibeIcons.Home),
        iconTextId = R.string.app_navigation_home,
        titleTextId = R.string.app_home_topbar_title
    ),

    MOVIES(
        icon = DrawableResourceIcon(VibeIcons.Movies),
        iconTextId = R.string.app_navigation_movies,
        titleTextId = R.string.app_movies_topbar_title
    ),

    SHOWS(
        icon = DrawableResourceIcon(VibeIcons.TvShow),
        iconTextId = R.string.app_navigation_tv_shows,
        titleTextId = R.string.app_tv_shows_topbar_title
    ),

    /*FAVOURITES(
        icon = DrawableResourceIcon(VibeIcons.Favourites),
        iconTextId = R.string.app_navigation_favourites,
        titleTextId = R.string.app_favourites_topbar_title
    ),*/
}