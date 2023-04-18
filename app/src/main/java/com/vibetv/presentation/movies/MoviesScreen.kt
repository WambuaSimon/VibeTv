package com.vibetv.presentation.movies

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vibetv.R
import com.vibetv.common.Constants
import com.vibetv.common.shimmerBrush
import com.vibetv.common.utils.Resource
import com.vibetv.presentation.movies.components.MovieGenre


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    genreState: Resource<GenreState>,
    allMoviesModel: AllMoviesModel,
    onGenreClicked: (Int) -> Unit,
    onDetailsClick: (Int) -> Unit,
) {
    val state = allMoviesModel.state

    val errorState = allMoviesModel.error

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val fallbackError = stringResource(R.string.home_fallback_error_message)
    val errorButton = stringResource(R.string.home_fallback_error_button)
    val errorMessage = remember(errorState, context, fallbackError) {
        when {
            errorState != null ->
                errorState.errorMessage(context) ?: fallbackError

            else -> null
        }
    }

    errorMessage?.let {
        LaunchedEffect(it) {
            snackbarHostState
                .showSnackbar(message = it, duration = SnackbarDuration.Long)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        MovieGenre(
            modifier = modifier,
            genreState = genreState,
            onGenreClicked = onGenreClicked,

            )

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { contentPadding ->
            Crossfade(state, label = "") { state ->
                when (state) {

                    Resource.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }

                    }

                    is Resource.Success, is Resource.Error -> MovieContent(
                        modifier,
                        allMoviesState = state,
                        onDetailsClick = onDetailsClick,
                        contentPadding = contentPadding
                    )

                }
            }
        }

    }
}

@Composable
fun MovieContent(
    modifier: Modifier,
    contentPadding: PaddingValues,
    onDetailsClick: (Int) -> Unit,
    allMoviesState: Resource<AllMoviesState>,

    ) {

    val allMovies = allMoviesState.result?.allMovies

    LazyColumn(modifier = modifier.padding(contentPadding)) {
        items(allMovies.orEmpty()) { movie ->

            Row(
                modifier = modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(10.dp),
                    modifier = modifier
                        .padding(end = 16.dp, bottom = 16.dp)
                        .clickable { onDetailsClick(movie.id) }
                ) {
                    AsyncImage(
                        modifier = modifier
                            .background(
                                shimmerBrush(
                                    targetValue = 1300f,
                                    showShimmer = true
                                )
                            )
                            .height(150.dp)
                            .width(100.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(Constants.POSTER_PATH + movie.poster_path)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,

                        )

                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = movie.overview,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "Released: " + movie.release_date,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = modifier.fillMaxWidth(),

                        ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.StarHalf,
                                contentDescription = null
                            )
                            Text(
                                text = "${movie.vote_average}",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }

                        Spacer(modifier = modifier.width(50.dp))
                        AnimatedVisibility(visible = !movie.adult) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Warning,
                                    contentDescription = null
                                )
                                Text(
                                    text = "PG",
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }

                    }
                }


            }


        }
    }
}


