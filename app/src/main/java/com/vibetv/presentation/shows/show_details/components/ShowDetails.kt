package com.vibetv.presentation.shows.show_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vibetv.core.data.entities.show_details.ShowDetailsResponseEntity
import com.vibetv.designSystem.components.AsyncImageBackdrop

@Composable
fun ShowDetails(
    modifier: Modifier,
    result: ShowDetailsResponseEntity?,

    ) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        AsyncImageBackdrop(
            modifier = modifier,
            backdropImage = result?.backdrop_path
        )
        ShowSideDetails(modifier = modifier, result = result)

    }


}






