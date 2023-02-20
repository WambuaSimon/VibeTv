package com.vibetv.presentation.shows.show_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.vibetv.common.utils.Resource
import com.vibetv.common.utils.ViewState
import com.vibetv.core.data.entities.season.EpisodeEntity

class EpisodeModel(
    state: ViewState<Resource<EpisodeModelPageState>> = ViewState.Empty,
    error: Resource.Error<Unit>? = null
) {
    var state by mutableStateOf(state)
    var error: Resource.Error<Unit>? by mutableStateOf(error)

}

data class EpisodeModelPageState(
    val episode: EpisodeEntity? = null
)