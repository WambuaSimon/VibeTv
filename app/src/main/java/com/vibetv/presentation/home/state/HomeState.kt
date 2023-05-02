package com.vibetv.presentation.home.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.vibetv.common.utils.Resource
import com.vibetv.core.data.entities.NowPlayingResultEntity
import com.vibetv.core.data.entities.PopularResultEntity
import com.vibetv.core.data.entities.TopRatedResultEntity
import com.vibetv.core.data.entities.TrendingEntity
import kotlinx.coroutines.flow.MutableStateFlow

class HomeModel(
    error: Resource.Error<Unit>? = null,
) {
    val selectedFilterOption = MutableStateFlow(TimeWindow.day)
    var error: Resource.Error<Unit>? by mutableStateOf(error)
}
data class HomePageState(
    val nowPlaying: List<NowPlayingResultEntity>? = emptyList(),
    val popular: List<PopularResultEntity>? = emptyList(),
    val topRated: List<TopRatedResultEntity>? = emptyList(),
    val trending: List<TrendingEntity>? = emptyList()
)

enum class TimeWindow {
    day,
    week
}
