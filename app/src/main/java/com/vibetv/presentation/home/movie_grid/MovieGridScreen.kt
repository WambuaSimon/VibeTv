package com.vibetv.presentation.home.movie_grid

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vibetv.designSystem.components.MovieCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieGridScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    onMovieDetailsClick: (Int) -> Unit,
    model: MovieGridModel,
    title: String
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
                Crossfade(targetState = model.nowPlaying, label = "movie_grid") { nowPlaying ->
                    LazyVerticalGrid(
                        modifier = modifier.padding(contentPadding),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        state = listState,
                        columns = GridCells.Adaptive(110.dp),
                        content = {
                            if (!nowPlaying.isNullOrEmpty()) {
                                items(nowPlaying.size) { index ->
                                    val item = nowPlaying[index]
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