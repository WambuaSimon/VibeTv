package com.vibetv.presentation.shows

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.vibetv.common.utils.Resource
import com.vibetv.core.data.entities.shows.AiringTodayEntity
import com.vibetv.core.data.entities.shows.LatestShowEntity
import com.vibetv.core.data.entities.shows.PopularShowsEntity
import com.vibetv.core.data.entities.shows.TopRatedShowsEntity

class ShowModel (
    error: Resource.Error<Unit>? = null,
){
    var error: Resource.Error<Unit>? by mutableStateOf(error)
}


data class ShowPageState(
    var airingToday: List<AiringTodayEntity>? = emptyList(),
    var latestShow: List<LatestShowEntity>? = emptyList(),
    var popularShow: List<PopularShowsEntity>? = emptyList(),
    var topRatedShow: List<TopRatedShowsEntity>? = emptyList()
)