package com.vibetv.presentation.movie_details

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.Timelapse
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vibetv.common.Constants
import com.vibetv.common.utils.ViewState
import com.vibetv.designSystem.components.AsyncImageBackdrop
import com.vibetv.designSystem.components.EmptyScreen
import com.vibetv.presentation.movie_details.state.MovieDetailsModel
import java.text.NumberFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun MovieDetailsScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    model: MovieDetailsModel
) {
    val snackbarHostState = remember { SnackbarHostState() }


    when (val state = model.state) {
        ViewState.Empty -> Unit

        ViewState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is ViewState.Ready -> {
            val result = state.value.result?.movieDetails
            val format = NumberFormat.getCurrencyInstance(Locale("en", "US"))

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                result?.title.orEmpty(),
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        navigationIcon = {
                            Icon(
                                Icons.Rounded.ArrowBack,
                                contentDescription = null,
                                modifier.clickable { onNavigateUp() }
                            )
                        },

                        )
                },
                snackbarHost = { SnackbarHost(snackbarHostState) },
            ) { contentPadding ->

                Crossfade(targetState = result, label = "") { result ->
                    if (result == null) {
                        EmptyScreen(modifier = modifier)
                    } else {

                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .padding(contentPadding)
                                .padding(16.dp)
                        ) {
                            AsyncImageBackdrop(
                                modifier = modifier,
                                backdropImage = result.backdrop_path
                            )

                            Row(
                                modifier = modifier
                                    .padding(top = 16.dp)
                                    .fillMaxWidth(),
                            ) {
                                Card(
                                    elevation = CardDefaults.cardElevation(10.dp),
                                    modifier = modifier.padding(end = 16.dp, bottom = 16.dp)
                                ) {
                                    AsyncImage(
                                        modifier = modifier
                                            .height(200.dp)
                                            .width(150.dp),
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(Constants.POSTER_PATH + result.poster_path)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = null,
                                        contentScale = ContentScale.FillBounds,

                                        )

                                }
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Row(
                                        modifier = modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.StarHalf,
                                                contentDescription = null
                                            )
                                            Text(
                                                text = "${result.vote_average}" + "/(${result.vote_count})",
                                                style = MaterialTheme.typography.labelMedium
                                            )
                                        }

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.Timelapse,
                                                contentDescription = null
                                            )
                                            Text(
                                                text = "${result.runtime}" + " mins",
                                                style = MaterialTheme.typography.labelMedium
                                            )
                                        }
                                    }
                                    Row(
                                        modifier = modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = "Status:")
                                        Text(text = result.status)
                                    }

                                    Row(
                                        modifier = modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = "Release date:")
                                        Text(text = result.release_date)
                                    }
                                    Row(
                                        modifier = modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = "Revenue:")
                                        Text("${result.revenue} USD")
                                    }
                                    Row {
                                        IconButton(onClick = { }) {
                                            Icon(
                                                imageVector = Icons.Rounded.Favorite,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.secondary
                                            )
                                        }
                                        Spacer(modifier = modifier.width(50.dp))
                                        IconButton(onClick = { }) {
                                            Icon(
                                                imageVector = Icons.Rounded.Link,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.secondary
                                            )
                                        }
                                    }

                                }


                            }
                            Text(text = "Genre(s)", style = MaterialTheme.typography.titleMedium)
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                result.genres.map {
                                    Text(
                                        text = it.name.plus(" "),
                                        style = MaterialTheme.typography.bodySmall
                                    )

                                }
                            }

                            Spacer(modifier = modifier.height(16.dp))
                            Text(text = "Overview", style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = result.overview,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = modifier.height(16.dp))
                            if (result.production_companies.isNotEmpty()) {
                                Text(
                                    text = "Production Companies",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                LazyRow(
                                    modifier = modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                                    verticalAlignment = Alignment.CenterVertically

                                ) {
                                    items(result.production_companies) {
                                        AsyncImage(
                                            modifier = modifier,
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(Constants.LOGO_SIZE + it.logo_path)
                                                .crossfade(true)
                                                .build(),
                                            contentDescription = null,
                                            contentScale = ContentScale.FillBounds,
                                        )

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}






