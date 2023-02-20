package com.vibetv.presentation.shows.show_details.components

import android.util.Log
import android.view.View
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vibetv.common.utils.ViewState
import com.vibetv.core.model.season_details.Episode
import com.vibetv.designSystem.components.EmptyScreen
import com.vibetv.presentation.shows.show_details.EpisodeModel
import com.vibetv.presentation.shows.show_details.SeasonDetailsModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeasonDetails(
    modifier: Modifier,
    model: SeasonDetailsModel,
    episodeModel: EpisodeModel,
    onClickEpisode: (Int, Int) -> Unit
) {

    val scope = rememberCoroutineScope()
    val skipHalfExpanded by remember { mutableStateOf(false) }
    val bottomSheetState = rememberSheetState(skipHalfExpanded = true)
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }


    when (val state = model.state) {
        ViewState.Empty -> {
            EmptyScreen(modifier = modifier)
        }

        ViewState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter,
            ) {
                CircularProgressIndicator()
            }
        }

        is ViewState.Ready -> {
            val result = state.value.result?.seasonDetails


            if (openBottomSheet) {
                when (val episodeState = episodeModel.state) {
                    ViewState.Empty -> {
                       EmptyScreen(modifier = modifier)
                    }

                    ViewState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.BottomCenter,
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is ViewState.Ready -> {
                        val episodeResult = episodeState.value.result?.episode

                        ModalBottomSheet(
                            onDismissRequest = { openBottomSheet = false },
                            sheetState = bottomSheetState,
                        ) {
                            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                                IconButton(onClick = {
                                    scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                                        if (!bottomSheetState.isVisible) {
                                            openBottomSheet = false
                                        }
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null,
                                        modifier = modifier.padding(horizontal = 8.dp)
                                    )
                                }


                            }

                            Column(
                                modifier = modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                Text(
                                    text = episodeResult?.name.orEmpty(),
                                    style = MaterialTheme.typography.titleLarge,
                                )
                                Text(
                                    text = "${episodeResult?.vote_average}" + "/(${episodeResult?.vote_count}) votes",
                                    style = MaterialTheme.typography.labelMedium,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = modifier.height(8.dp))
                                Text(text = "Aired on: " + episodeResult?.air_date.orEmpty())
                                Spacer(modifier = modifier.height(16.dp))

                                AnimatedVisibility(visible = episodeResult?.overview.isNullOrEmpty()) {
                                    Column(modifier = modifier) {
                                        Text(
                                            text = "Overview",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            text = episodeResult?.overview.orEmpty(),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }

                                }

                            }

                        }

                    }
                }
            }

            Column(modifier = modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                val episodes = result?.episodes
                for (item in episodes.orEmpty()) {
                    Card(modifier = modifier
                        .padding(vertical = 2.dp)
                        .clickable {
                            onClickEpisode(item.season_number, item.episode_number)
                            openBottomSheet = !openBottomSheet
                        }) {
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween

                        ) {
                            Box(Modifier.weight(0.9f)) {
                                Row(
                                    modifier = modifier,
                                    verticalAlignment = Alignment.CenterVertically,
                                )

                                {
                                    Box(
                                        modifier = modifier
                                            .padding(12.dp)
                                            .size(50.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.tertiary)
                                    ) {
                                        Text(
                                            text = "EP " + item.episode_number.toString(),
                                            style = MaterialTheme.typography.bodySmall,
                                            modifier = modifier.align(Alignment.Center),
                                            color = MaterialTheme.colorScheme.onPrimary,
                                        )
                                    }
                                    Column(
                                        modifier = modifier,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = item.name,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Text(
                                            text = item.overview,
                                            style = MaterialTheme.typography.bodySmall,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                            Box(modifier = modifier.weight(0.05f)) {
                                Icon(
                                    imageVector = Icons.Default.ArrowForwardIos,
                                    contentDescription = null
                                )
                            }
                        }


                    }
                }

            }
        }

    }

}








