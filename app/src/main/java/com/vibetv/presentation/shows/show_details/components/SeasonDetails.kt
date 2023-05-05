package com.vibetv.presentation.shows.show_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vibetv.common.utils.Resource
import com.vibetv.presentation.shows.show_details.EpisodeModel
import com.vibetv.presentation.shows.show_details.SeasonsDetailsPageState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeasonDetails(
    modifier: Modifier,
    episodeModel: EpisodeModel,
    state: Resource<SeasonsDetailsPageState>?,
    onClickEpisode: (Int, Int) -> Unit
) {

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val result = state?.result


    Column {
        val episodes = result?.seasonDetails?.episodes.orEmpty()
        for (item in episodes) {
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
                    /*  Box(modifier = modifier.weight(0.05f)) {
                          Icon(
                              imageVector = Icons.Default.ArrowForwardIos,
                              contentDescription = null
                          )
                      }*/
                }


            }
        }

    }


}












