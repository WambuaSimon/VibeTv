package com.vibetv.presentation.shows

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vibetv.R
import com.vibetv.common.utils.Resource
import com.vibetv.presentation.shows.components.AiringShowCard
import com.vibetv.presentation.shows.components.PopularShowCard
import com.vibetv.presentation.shows.components.TopRatedShowCard

@Composable
fun TvShowsScreen(
    state: Resource<ShowPageState>,
    modifier: Modifier = Modifier,
    model: ShowModel,
    onNavigateToShowDetails: (Int) -> Unit,
    onMoreClicked: (String, String?) -> Unit,
) {
    val errorState = model.error
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
            model.error = null
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
                    .padding(vertical = 8.dp)
            ) {
                AiringShowCard(
                    modifier = modifier,
                    result = result.airingToday.orEmpty(),
                    onNavigateToShowDetails = onNavigateToShowDetails,
                    onMoreClicked = onMoreClicked
                )
                // LatestShowCard(modifier = modifier, result = result?.latestShow.orEmpty())
                TopRatedShowCard(
                    modifier = modifier,
                    result = result.topRatedShow.orEmpty(),
                    onNavigateToShowDetails = onNavigateToShowDetails,
                    onMoreClicked = onMoreClicked
                )

                PopularShowCard(
                    modifier = modifier,
                    result = result.popularShow.orEmpty(),
                    onNavigateToShowDetails = onNavigateToShowDetails,
                    onMoreClicked = onMoreClicked
                )

            }

        }

    }
}