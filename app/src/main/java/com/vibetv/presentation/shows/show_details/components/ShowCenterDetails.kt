package com.vibetv.presentation.shows.show_details.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vibetv.common.Constants
import com.vibetv.core.data.entities.show_details.ShowDetailsResponseEntity
import java.util.logging.Filter

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ShowCenterDetails(
    modifier: Modifier,
    result: ShowDetailsResponseEntity?,
    onSeasonClicked: (Int) -> Unit,
) {
    var isSelected by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(0) }

        AnimatedVisibility(visible = !result?.genres.isNullOrEmpty() ) {
            Column {
                Text(text = "Genre(s)", style = MaterialTheme.typography.titleMedium)
               Spacer(modifier = modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    result?.genres?.map {

                        Text(text = it.name.plus(" "), style = MaterialTheme.typography.bodySmall)

                    }
                }
            }

    }


    Spacer(modifier = Modifier.height(16.dp))

    AnimatedVisibility (visible = !result?.overview.isNullOrEmpty()) {
        Column {
            Text(text = "Storyline", style = MaterialTheme.typography.titleMedium)
            Text(text = "${result?.overview}", style = MaterialTheme.typography.bodyMedium)
        }
    }
     Spacer(modifier = Modifier.height(16.dp))

    AnimatedVisibility(visible = !result?.created_by.isNullOrEmpty()) {
        Column {
            Text(text = "Created by", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(result?.created_by.orEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        AsyncImage(
                            modifier = modifier
                                .size(80.dp)
                                .clip(CircleShape),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(Constants.POSTER_PATH + it.profile_path)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                        Text(text = it.name)
                    }

                }

            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    AnimatedVisibility(visible = !result?.networks.isNullOrEmpty()) {
        Column {
            Text(text = "Can be streamed on", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(result?.networks.orEmpty()) {
                    AsyncImage(
                        modifier = modifier,
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(Constants.LOGO_SIZE + it.logo_path)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                }

            }
        }
    }
    Spacer(modifier = modifier.height(16.dp))

    AnimatedVisibility(visible = !result?.seasons.isNullOrEmpty()) {
        Column {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                itemsIndexed(result?.seasons.orEmpty()) { index, it ->
                    isSelected = selectedItem == index
                    FilterChip(
                        modifier = modifier,
                        onClick = {
                            onSeasonClicked(it.season_number)
                            selectedItem = if (selectedItem != index) index else 0

                        },
                        label = {
                            Text(
                                text = it.name, style = MaterialTheme.typography.bodySmall

                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent
                        ),
                        selected = isSelected

                    )
                }
            }
        }
    }

}