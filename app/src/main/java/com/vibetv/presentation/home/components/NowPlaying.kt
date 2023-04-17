package com.vibetv.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vibetv.core.data.entities.NowPlayingResultEntity
import com.vibetv.designSystem.components.MovieCard

@Composable
fun NowPlaying(
    modifier: Modifier,
    //nowPlaying: LazyPagingItems<NowPlayingResultEntity>,
    nowPlaying: List<NowPlayingResultEntity>? = emptyList(),
    onMovieDetailsClick: (Int) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(start = 8.dp, end = 8.dp)

    ) {
        /* items(nowPlaying.itemSnapshotList.take(5)) { nowPlayingMoviesItem ->
             if (nowPlayingMoviesItem != null) {
                 AnimatedVisibility(visible = nowPlayingMoviesItem.poster_path.isNullOrEmpty()) {
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
         }*/

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


        /* val loadState = nowPlaying.loadState.mediator
         item {
             if (loadState?.refresh == LoadState.Loading) {
                 Box(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(16.dp),
                     contentAlignment = Alignment.Center,
                 ) {
                     CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                 }
             }
             if (loadState?.append == LoadState.Loading) {
                 Box(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(16.dp),
                     contentAlignment = Alignment.Center,
                 ) {
                     CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                 }
             }

             if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
                 val isPaginatingError =
                     (loadState.append is LoadState.Error) || nowPlaying.itemCount > 1
                 val error = if (loadState.append is LoadState.Error)
                     (loadState.append as LoadState.Error).error
                 else
                     (loadState.refresh as LoadState.Error).error

                 val pagingModifier = if (isPaginatingError) {
                     Modifier.padding(8.dp)
                 } else {
                     Modifier.fillParentMaxSize()
                 }
                 Column(
                     modifier = pagingModifier,
                     verticalArrangement = Arrangement.Center,
                     horizontalAlignment = Alignment.CenterHorizontally,
                 ) {
                     if (!isPaginatingError) {
                         Icon(
                             modifier = Modifier
                                 .size(64.dp),
                             imageVector = Icons.Rounded.Warning, contentDescription = null
                         )
                     }

                     Text(
                         modifier = Modifier.padding(8.dp),
                         text = error.message ?: error.toString(),
                         textAlign = TextAlign.Center,
                     )

                     Button(
                         onClick = {
                             nowPlaying.refresh()
                         },
                         content = {
                             Text(text = "Refresh")
                         },
                         colors = ButtonDefaults.buttonColors(
                             containerColor = MaterialTheme.colorScheme.primary,
                             contentColor = Color.White,
                         )
                     )
                 }

             }
         }*/
    }
}
