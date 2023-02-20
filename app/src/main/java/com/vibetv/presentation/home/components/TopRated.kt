package com.vibetv.presentation.home.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vibetv.common.Constants
import com.vibetv.core.data.entities.TopRatedResultEntity
import com.vibetv.designSystem.components.MovieCard

@Composable
fun TopRated(
    modifier: Modifier,
    topRatedResponse: List<TopRatedResultEntity>? = emptyList(),
    onMovieDetailsClick: (Int) -> Unit

) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(
            start = 8.dp,
            end = 8.dp
        )

    ) {
        items(topRatedResponse.orEmpty()) { topRatedItem ->
            MovieCard(
                modifier = modifier,
                posterPathBaseUrl = Constants.POSTER_PATH,
                poster = topRatedItem.poster_path,
                voteAverage = topRatedItem.vote_average,
                id = topRatedItem.id,
                onClick = {
                    onMovieDetailsClick(topRatedItem.id)
                }
            )

        }

    }
}