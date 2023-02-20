package com.vibetv.presentation.shows.show_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vibetv.common.utils.ViewState
import com.vibetv.designSystem.components.EmptyScreen
import com.vibetv.presentation.shows.show_details.components.SeasonDetails
import com.vibetv.presentation.shows.show_details.components.ShowCenterDetails
import com.vibetv.presentation.shows.show_details.components.ShowDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowsDetailsScreen(
    modifier: Modifier = Modifier,
    model: ShowDetailsModel,
    onNavigateUp: () -> Unit,
    onClickSeason: (Int) -> Unit,
    seasonDetailsModel: SeasonDetailsModel,
    onClickEpisode: (Int, Int) -> Unit,
    episodeModel: EpisodeModel,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    when (val state = model.state) {
        ViewState.Empty -> {
            EmptyScreen(modifier = modifier)
        }

        ViewState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is ViewState.Ready -> {
            val result = state.value.result?.showDetails

            Scaffold(
                topBar = {
                    TopAppBar(title = {
                        Text(text = result?.name.orEmpty())
                    },
                        navigationIcon = {
                            Icon(
                                Icons.Rounded.ArrowBack,
                                contentDescription = null,
                                modifier.clickable { onNavigateUp() }
                            )
                        }
                    )
                },
                snackbarHost = {
                    SnackbarHost(snackbarHostState) {

                    }
                }
            ) { contentPadding ->
                Column(
                    modifier = modifier
                        .padding(contentPadding)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    ShowDetails(
                        modifier = modifier,
                        result = result,
                    )

                    ShowCenterDetails(
                        modifier = modifier,
                        result = result,
                        onSeasonClicked = onClickSeason,
                    )

                    SeasonDetails(
                        modifier = modifier,
                        model = seasonDetailsModel,
                        onClickEpisode = onClickEpisode,
                        episodeModel = episodeModel
                    )
                }

            }


        }

    }
}