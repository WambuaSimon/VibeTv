package com.vibetv.presentation.home.components

import android.content.Context.MODE_PRIVATE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vibetv.R
import com.vibetv.core.data.entities.TrendingEntity
import com.vibetv.designSystem.components.MovieCard
import com.vibetv.presentation.home.state.HomeModel
import com.vibetv.presentation.home.state.TimeWindow
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun Trending(
    modifier: Modifier,
    onMovieDetailsClick: (Int) -> Unit,
    trendingList: List<TrendingEntity>? = emptyList(),
    navigateToMovieGrid: (String, String?) -> Unit,
    homeModel: HomeModel

) {
    val options = TimeWindow.values().toList()
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    val context = LocalContext.current
    val prefs by lazy {
        context.getSharedPreferences("prefs", MODE_PRIVATE)
    }
    val scrollPosition = prefs.getInt("scroll_position", 0)

    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.home_trending_title),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = modifier.width(16.dp))
            ExposedDropdownMenuBox(
                modifier = modifier.width(150.dp),
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                TextField(
                    // The `menuAnchor` modifier must be passed to the text field for correctness.
                    modifier = Modifier
                        .menuAnchor()
                        .height(50.dp),
                    readOnly = true,
                    value = selectedOptionText.name,
                    onValueChange = {},
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },

                    )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption.name) },
                            onClick = {
                                homeModel.selectedFilterOption.value = selectionOption
                                //homeModel.timeWindow = selectionOption
                                //Log.d("Stuff2", "Time Window: " + selectionOption)
                                selectedOptionText = selectionOption
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
            Spacer(modifier = modifier.weight(1f))
            TextButton(
                onClick = {
                    navigateToMovieGrid(
                        context.getString(R.string.home_trending_title),
                        selectedOptionText.name
                    )
                }

            ) {
                Text(
                    text = stringResource(id = R.string.home_popular_movies_action),
                    style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.tertiary),
                )
            }
        }
        val lazyListsState = rememberLazyListState(
            initialFirstVisibleItemIndex = scrollPosition
        )

        LaunchedEffect(lazyListsState) {
            snapshotFlow { lazyListsState.firstVisibleItemIndex }.debounce(500L)
                .collectLatest { index ->
                    prefs.edit().putInt("scroll_position", index).apply()
                }
        }
        LazyRow(
            state = lazyListsState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(start = 8.dp, end = 8.dp)

        ) {
            items(trendingList.orEmpty()) { trending ->
                MovieCard(
                    modifier = modifier,
                    poster = trending.poster_path.orEmpty(),
                    voteAverage = trending.vote_average,
                    id = trending.id,
                    onClick = {
                        onMovieDetailsClick(trending.id)
                    }
                )
            }
        }
    }
}