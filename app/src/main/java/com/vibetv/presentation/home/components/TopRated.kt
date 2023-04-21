package com.vibetv.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vibetv.R
import com.vibetv.core.data.entities.TopRatedResultEntity
import com.vibetv.designSystem.components.MovieCard
import com.vibetv.designSystem.components.MovieHeader

@Composable
fun TopRated(
    modifier: Modifier,
    topRatedResponse: List<TopRatedResultEntity>? = emptyList(),
    onMovieDetailsClick: (Int) -> Unit,
    navigateToMovieGrid: (String) -> Unit,
) {
    Column(modifier = modifier) {
        MovieHeader(
            modifier = modifier,
            title = R.string.home_top_rated_title,
            onMovieGridClicked = navigateToMovieGrid,
            actionText = null
        )
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
                    poster = topRatedItem.poster_path.orEmpty(),
                    voteAverage = topRatedItem.vote_average,
                    id = topRatedItem.id,
                    onClick = {
                        onMovieDetailsClick(topRatedItem.id)
                    }
                )

            }

        }
    }

}