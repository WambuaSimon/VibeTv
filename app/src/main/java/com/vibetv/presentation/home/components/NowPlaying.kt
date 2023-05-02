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
import com.vibetv.core.data.entities.NowPlayingResultEntity
import com.vibetv.designSystem.components.MovieCard
import com.vibetv.designSystem.components.MovieHeader

@Composable
fun NowPlaying(
    modifier: Modifier,
    nowPlaying: List<NowPlayingResultEntity>? = emptyList(),
    onMovieDetailsClick: (Int) -> Unit,
    navigateToMovieGrid: (String, String?) -> Unit,
) {
    Column(modifier = modifier) {
        MovieHeader(
            modifier = modifier,
            title = R.string.home_now_showing_title,
            actionText = R.string.home_now_showing_action,
            onMovieGridClicked = navigateToMovieGrid,

            )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(start = 8.dp, end = 8.dp)

        ) {

            items(nowPlaying.orEmpty()) { nowPlayingMoviesItem ->

                MovieCard(
                    modifier = modifier,
                    poster = nowPlayingMoviesItem.poster_path.orEmpty(),
                    voteAverage = nowPlayingMoviesItem.vote_average,
                    id = nowPlayingMoviesItem.id,
                    onClick = {
                        onMovieDetailsClick(nowPlayingMoviesItem.id)
                    }
                )
            }


        }


    }
}
