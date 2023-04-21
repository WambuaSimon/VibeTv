package com.vibetv.presentation.shows.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import com.vibetv.R
import com.vibetv.common.Constants
import com.vibetv.core.data.entities.shows.TopRatedShowsEntity
import com.vibetv.designSystem.components.MovieHeader

@Composable
fun TopRatedShowCard(
    modifier: Modifier,
    result: List<TopRatedShowsEntity>,
    onNavigateToShowDetails: (Int) -> Unit,
    onMoreClicked: (String,String?) -> Unit
) {
    Column {

        MovieHeader(
            modifier = modifier,
            title = R.string.shows_top_rated,
            actionText = R.string.home_now_showing_action,
            onMovieGridClicked = onMoreClicked
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(
                start = 8.dp,
                end = 8.dp
            )
        ) {
            items(result) { result ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Card(modifier = Modifier.clickable { onNavigateToShowDetails(result.id) }) {
                        AsyncImage(
                            modifier = modifier
                                .height(150.dp)
                                .width(100.dp),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(Constants.POSTER_PATH + result.poster_path)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,

                            )
                    }
                    Row {
                        Icon(
                            Icons.Outlined.StarHalf,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = modifier.width(4.dp))
                        Text(
                            text = result.vote_average.toString(),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}