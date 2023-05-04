package com.vibetv.presentation.home.movie_grid

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.vibetv.core.data.entities.NowPlayingResultEntity
import com.vibetv.designSystem.components.MovieCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieGridScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    onMovieDetailsClick: (Int) -> Unit,
    model: MovieGridModel,
    title: String,
    nowPlayingResultEntity: LazyPagingItems<NowPlayingResultEntity>,
) {
    val listState = rememberLazyGridState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = model.title)
            },
                navigationIcon = {
                    IconButton(onClick = { onNavigateUp() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        when (title) {
            "Now Showing" -> {
                Crossfade(
                    targetState = nowPlayingResultEntity,
                    label = "movie_grid"
                ) { movie ->
                    val loadState = movie.loadState.mediator
                    if (loadState?.refresh == LoadState.Loading) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = "Refresh Loading"
                            )

                            CircularProgressIndicator()
                        }
                    }
                    if (loadState?.append == LoadState.Loading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
                        val isPaginatingError =
                            (loadState.append is LoadState.Error) || movie.itemCount > 1
                        val error = if (loadState.append is LoadState.Error)
                            (loadState.append as LoadState.Error).error
                        else
                            (loadState.refresh as LoadState.Error).error

                        if (isPaginatingError) {
                            Modifier.padding(8.dp)
                        } else {
                            Modifier.fillMaxSize()
                        }
                        AnimatedVisibility(visible = nowPlayingResultEntity.itemCount > 0) {

                            Column(
                                modifier = modifier,
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                if (!isPaginatingError) {
                                    Icon(
                                        modifier = Modifier
                                            .size(64.dp),
                                        imageVector = Icons.Rounded.Warning,
                                        contentDescription = null
                                    )
                                }

                                Text(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    text = error.message ?: error.toString(),
                                    textAlign = TextAlign.Center,
                                )

                                Button(
                                    onClick = {
                                        movie.refresh()
                                    },
                                    content = {
                                        Text(text = "Refresh")
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = Color.White,
                                    )
                                )
                            }
                        }
                    }

                    LazyVerticalGrid(
                        modifier = modifier.padding(contentPadding),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        state = listState,
                        columns = GridCells.Adaptive(110.dp),
                        content = {
                            items(nowPlayingResultEntity.itemCount) { index ->
                                val playingItem = nowPlayingResultEntity[index]
                                if (playingItem != null) {
                                    MovieCard(
                                        modifier = modifier,
                                        poster = playingItem.poster_path.orEmpty(),
                                        voteAverage = playingItem.vote_average,
                                        onClick = {
                                            onMovieDetailsClick(
                                                playingItem.id
                                            )
                                        },
                                        id = playingItem.id
                                    )
                                }
                            }
                        }
                    )

                }
            }

            "Popular movies" -> {
                Crossfade(targetState = model.popular, label = "movie_grid") { popular ->
                    LazyVerticalGrid(
                        modifier = modifier.padding(contentPadding),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        state = listState,
                        columns = GridCells.Adaptive(110.dp),
                        content = {
                            if (!popular.isNullOrEmpty()) {
                                items(popular.size) { index ->
                                    val item = popular[index]
                                    if (!item.poster_path.isNullOrEmpty()) {
                                        MovieCard(
                                            modifier = modifier,
                                            poster = item.poster_path,
                                            voteAverage = item.vote_average,
                                            onClick = {
                                                onMovieDetailsClick(item.id)
                                            },
                                            id = item.id
                                        )
                                    }
                                }
                            }
                        }
                    )

                }

            }

            "Trending" -> {
                Crossfade(targetState = model.trending, label = "movie_grid") { trending ->
                    LazyVerticalGrid(
                        modifier = modifier.padding(contentPadding),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        state = listState,
                        columns = GridCells.Adaptive(110.dp),
                        content = {
                            if (!trending.isNullOrEmpty()) {
                                items(trending.size) { index ->
                                    val item = trending[index]
                                    if (!item.poster_path.isNullOrEmpty()) {
                                        MovieCard(
                                            modifier = modifier,
                                            poster = item.poster_path,
                                            voteAverage = item.vote_average,
                                            onClick = {
                                                onMovieDetailsClick(item.id)
                                            },
                                            id = item.id
                                        )
                                    }
                                }
                            }
                        }
                    )

                }


            }

        }


    }
}