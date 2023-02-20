package com.vibetv.presentation.home.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.vibetv.common.utils.Resource
import com.vibetv.common.utils.ViewState
import com.vibetv.core.data.entities.NowPlayingResultEntity
import com.vibetv.core.data.entities.PopularResultEntity
import com.vibetv.core.data.entities.TopRatedResultEntity


class HomeModel(
    state: ViewState<Resource<HomePageState>> = ViewState.Empty,
    error: Resource.Error<Unit>? = null,
) {
    var state by mutableStateOf(state)
    var error: Resource.Error<Unit>? by mutableStateOf(error)
}

data class HomePageState(
    var nowPlaying: List<NowPlayingResultEntity>? = emptyList(),
    val popular: List<PopularResultEntity>? = emptyList(),
    val topRated: List<TopRatedResultEntity>? = emptyList()
)