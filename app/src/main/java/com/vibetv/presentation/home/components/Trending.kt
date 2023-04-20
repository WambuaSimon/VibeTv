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
import com.vibetv.core.data.entities.TrendingEntity
import com.vibetv.designSystem.components.MovieCard
import com.vibetv.designSystem.components.MovieHeader

@Composable
fun Trending(
    modifier: Modifier,
    onMovieDetailsClick: (Int) -> Unit,
    trendingList: List<TrendingEntity>? = emptyList(),
    navigateToMovieGrid: () -> Unit,
) {
    Column(modifier = modifier) {
        MovieHeader(
            modifier = modifier,
            title = R.string.home_trending_title,
            onMovieGridClicked = navigateToMovieGrid,
            actionText = null
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(start = 8.dp, end = 8.dp)

        ) {
            items(trendingList.orEmpty()) { trending ->
                MovieCard(
                    modifier = modifier,
                    poster = trending.poster_path.orEmpty(),
                    voteAverage = trending.vote_average,
                    id = trending.id,
                    onClick = {
                        onMovieDetailsClick(trending.id)
                    }
                )
            }
        }
    }
}