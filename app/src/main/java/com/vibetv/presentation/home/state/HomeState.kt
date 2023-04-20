package com.vibetv.presentation.home.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.vibetv.common.utils.Resource
import com.vibetv.core.data.entities.NowPlayingResultEntity
import com.vibetv.core.data.entities.PopularResultEntity
import com.vibetv.core.data.entities.TopRatedResultEntity
import com.vibetv.core.data.entities.TrendingEntity


class HomeModel(
    timeWindow: String = "day",
    error: Resource.Error<Unit>? = null,
) {
    var timeWindow: String by mutableStateOf(timeWindow)
    var error: Resource.Error<Unit>? by mutableStateOf(error)

}

data class HomePageState(
    val nowPlaying: List<NowPlayingResultEntity>? = emptyList(),
    val popular: List<PopularResultEntity>? = emptyList(),
    val topRated: List<TopRatedResultEntity>? = emptyList(),
    val trending: List<TrendingEntity>? = emptyList()
)