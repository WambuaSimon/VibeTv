package com.vibetv.presentation.shows.show_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.vibetv.common.utils.Resource
import com.vibetv.core.data.entities.season.SeasonDetailsEntity

class SeasonDetailsModel(
    state: Resource<SeasonsDetailsPageState>? = Resource.Loading,
    error: Resource.Error<Unit>? = null
) {
    var state by mutableStateOf(state)
    var error: Resource.Error<Unit>? by mutableStateOf(error)

}

data class SeasonsDetailsPageState(
    val seasonDetails: SeasonDetailsEntity? = null
)