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
import com.vibetv.core.data.entities.PopularResultEntity
import com.vibetv.designSystem.components.MovieCard
import com.vibetv.designSystem.components.MovieHeader

@Composable
fun PopularMovies(
    modifier: Modifier,
    popularResponse: List<PopularResultEntity>? = emptyList(),
    onMovieDetailsClick: (Int) -> Unit,
    navigateToMovieGrid: (String) -> Unit,
) {
    Column(modifier = modifier) {
        MovieHeader(
            modifier = modifier,
            title = R.string.home_popular_movies_title,
            actionText = R.string.home_popular_movies_action,
            onMovieGridClicked = navigateToMovieGrid,
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(
                start = 8.dp,
                end = 8.dp
            )

        ) {
            items(popularResponse.orEmpty()) { popularMoviesItem ->
                MovieCard(
                    modifier = modifier,
                    poster = popularMoviesItem.poster_path.orEmpty(),
                    voteAverage = popularMoviesItem.vote_average,
                    id = popularMoviesItem.id,
                    onClick = {
                        onMovieDetailsClick(popularMoviesItem.id)
                    }
                )

            }

        }
    }


}