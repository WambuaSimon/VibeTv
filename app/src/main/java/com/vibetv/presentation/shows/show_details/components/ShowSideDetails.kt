package com.vibetv.presentation.shows.show_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BatchPrediction
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.VideoLibrary
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vibetv.common.Constants
import com.vibetv.core.data.entities.show_details.ShowDetailsResponseEntity
import com.vibetv.designSystem.components.EmptyScreen

@Composable
fun ShowSideDetails(
    modifier:Modifier,
    result: ShowDetailsResponseEntity?,
){
    if(result?.backdrop_path?.isEmpty() == true){
        EmptyScreen(modifier = modifier)
    }

    Row(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
    ) {
        Card(
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = modifier.padding(end = 16.dp, bottom = 16.dp)
        ) {
            AsyncImage(
                modifier = modifier
                    .height(200.dp)
                    .width(150.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Constants.POSTER_PATH + result?.poster_path.orEmpty())
                    .crossfade(true)
                    .crossfade(300)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,

                )

        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.VideoLibrary,
                        contentDescription = null
                    )
                    Text(
                        text = "${result?.number_of_episodes} " + " Episodes",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.BatchPrediction,
                        contentDescription = null
                    )
                    Text(
                        text = "${result?.seasons?.size}" + " Season(s)",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Status:")
                Text(text = "${result?.status}")
            }

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "First Aired:")
                Text(text = "${result?.first_air_date}")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Rounded.Link,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.StarHalf,
                        contentDescription = null
                    )
                    Text(
                        text = "${result?.vote_average}" + "/(${result?.vote_count})",
                        style = MaterialTheme.typography.labelMedium
                    )
                }

            }

        }
    }
}