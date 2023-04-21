package com.vibetv.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.vibetv.R
import com.vibetv.common.utils.Resource
import com.vibetv.designSystem.theme.ShowErrorSnackbar
import com.vibetv.presentation.home.components.NowPlaying
import com.vibetv.presentation.home.components.PopularMovies
import com.vibetv.presentation.home.components.TopRated
import com.vibetv.presentation.home.components.Trending
import com.vibetv.presentation.home.state.HomeModel
import com.vibetv.presentation.home.state.HomePageState
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    state: Resource<HomePageState>,
    navigateToMovieDetails: (id: Int) -> Unit,
    navigateToMovieGrid: (String) -> Unit,
    onRefreshClick: () -> Unit,
    homeModel: HomeModel,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val currentUiState by rememberUpdatedState(state)
    val errorState = homeModel.error
    LaunchedEffect(context, snackbarHostState) {
        snapshotFlow { currentUiState }
            .collectLatest {
                if (it is Resource.Error) {
                    ShowErrorSnackbar(
                        context = context,
                        snackbarHostState = snackbarHostState,
                        error = errorState,
                        fallbackMessage = R.string.home_fallback_error_message,
                        retryButtonLabel = R.string.error_retry_button,
                        retry = onRefreshClick,
                    )
                }
            }

    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            onRefreshClick()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { contentPadding ->
        if (state is Resource.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        if (state is Resource.Success) {
            val result = state.result
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(contentPadding)
            ) {

                TopRated(
                    modifier = modifier,
                    topRatedResponse = result.topRated,
                    onMovieDetailsClick = navigateToMovieDetails,
                    navigateToMovieGrid = navigateToMovieGrid
                )

                Trending(
                    modifier = modifier,
                    onMovieDetailsClick = navigateToMovieDetails,
                    trendingList = result.trending,
                    navigateToMovieGrid = navigateToMovieGrid,
                    homeModel = homeModel
                )


                NowPlaying(
                    nowPlaying = result.nowPlaying,
                    modifier = Modifier,
                    onMovieDetailsClick = navigateToMovieDetails,
                    navigateToMovieGrid = navigateToMovieGrid

                )

                PopularMovies(
                    modifier = modifier,
                    popularResponse = result.popular,
                    onMovieDetailsClick = navigateToMovieDetails,
                    navigateToMovieGrid = navigateToMovieGrid
                )

            }
        }
    }
}





