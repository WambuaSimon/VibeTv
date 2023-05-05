package com.vibetv.presentation.shows.show_details.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vibetv.common.utils.ViewState
import com.vibetv.presentation.shows.show_details.EpisodeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeDetails(
    episodeModel: EpisodeModel,
    modifier: Modifier,
    openSheet: Boolean = false,
    scope: CoroutineScope
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(openSheet) }

    val skipHalfExpanded by remember { mutableStateOf(false) }
    val bottomSheetState = rememberSheetState(skipHalfExpanded = false)


    when (val episodeState = episodeModel.state) {
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
                onDismissRequest = {
                    scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                        if (!bottomSheetState.isVisible) {
                            openBottomSheet = !openBottomSheet
                        }
                    }
                },
                sheetState = bottomSheetState,
            ) {
                CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                    IconButton(onClick = {
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                openBottomSheet = !openBottomSheet
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

        else -> {}
    }
}
