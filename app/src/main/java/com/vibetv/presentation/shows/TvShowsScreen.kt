package com.vibetv.presentation.shows

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vibetv.R
import com.vibetv.common.utils.ViewState
import com.vibetv.designSystem.components.EmptyScreen
import com.vibetv.presentation.shows.components.AiringShowCard
import com.vibetv.presentation.shows.components.PopularShowCard
import com.vibetv.presentation.shows.components.TopRatedShowCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvShowsScreen(
    modifier: Modifier = Modifier,
    model: ShowModel,
    onNavigateToShowDetails: (Int) -> Unit
) {
    val state = model.state
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
        when (state) {
            ViewState.Empty -> {
                EmptyScreen(modifier = modifier)
            }

            ViewState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is ViewState.Ready -> {
                val result = state.value.result
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    AiringShowCard(
                        modifier = modifier,
                        result = result?.airingToday.orEmpty(),
                        onNavigateToShowDetails = onNavigateToShowDetails
                    )
                    // LatestShowCard(modifier = modifier, result = result?.latestShow.orEmpty())
                    TopRatedShowCard(
                        modifier = modifier,
                        result = result?.topRatedShow.orEmpty(),
                        onNavigateToShowDetails = onNavigateToShowDetails
                    )

                    PopularShowCard(
                        modifier = modifier,
                        result = result?.popularShow.orEmpty(),
                        onNavigateToShowDetails = onNavigateToShowDetails
                    )

                }

            }

        }
    }
}