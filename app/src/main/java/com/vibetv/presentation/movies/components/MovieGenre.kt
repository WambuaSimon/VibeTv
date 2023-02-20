package com.vibetv.presentation.movies.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vibetv.common.utils.ViewState
import com.vibetv.presentation.movies.GenreModel

@Composable
fun MovieGenre(
    modifier: Modifier,
    onGenreClicked: (Int) -> Unit,
    genreModel: GenreModel,

    ) {
    var isSelected by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }


    when (val state = genreModel.state) {
        ViewState.Empty -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }

        ViewState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }

        is ViewState.Ready -> {

            val allGenres = state.value.result?.genreList

            LazyRow(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(allGenres?.sortedBy { it.name }.orEmpty()) {
                    isSelected = selectedItem == it.name
                    AssistChip(
                        onClick = {
                            onGenreClicked(it.id!!)
                            selectedItem = if (selectedItem != it.name) it.name.orEmpty() else ""
                        },

                        label = {
                            Text(
                                text = it.name.orEmpty(),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color =
                                    if (isSelected) MaterialTheme.colorScheme.onPrimary else Color.Black
                                )
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent
                        )

                    )

                }
            }
        }

    }
}
