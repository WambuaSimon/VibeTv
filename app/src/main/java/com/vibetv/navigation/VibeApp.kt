@file:OptIn(ExperimentalMaterial3Api::class)

package com.vibetv.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.vibetv.presentation.home.navigation.homeNavigationRoute
import com.vibetv.presentation.movies.navigation.movieNavigationRoute
import com.vibetv.presentation.shows.navigation.showNavigationRoute
import com.vibetv.designSystem.theme.Icon
import com.vibetv.designSystem.theme.VibeNavigationBar
import com.vibetv.designSystem.theme.VibeNavigationBarItem
import com.vibetv.designSystem.theme.VibeTvTopAppBar


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VibeApp(
    appState: VibeAppState = rememberVibeAppState()
) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            VibeBottomBar(
                destinations = appState.topLevelDestinations,
                onNavigateToDestination = appState::navigateToTopLevelDestination,
                currentDestination = appState.currentDestination,
            )
        }
    ) { padding ->
        Row(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal
                    )
                )
        ) {
            Column(Modifier.fillMaxSize()) {
                val destination = appState.currentTopLevelDestination
                if (destination != null) {
                    VibeTvTopAppBar(
                        titleRes = destination.titleTextId,
                        actionIcon = Icons.Default.Settings,
                        actionIconContentDescription = null,
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        onActionClick = { }
                    )
                }
                VibeNavHost(
                    navController = appState.navController,

                    )
            }


        }
    }

}

@Composable
private fun VibeBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    VibeNavigationBar(
        modifier = modifier
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            VibeNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    when (val icon = destination.icon) {
                        is Icon.ImageVectorIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = null
                        )

                        is Icon.DrawableResourceIcon -> Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = null
                        )

                        else -> Unit
                    }
                },
                label = {
                    Text(stringResource(destination.iconTextId))
                }
            )
        }
    }


}

val TopLevelDestination.route: String
    get() = when (this) {
        TopLevelDestination.HOME -> homeNavigationRoute
        TopLevelDestination.MOVIES -> movieNavigationRoute
        TopLevelDestination.SHOWS -> showNavigationRoute

    }

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false