package com.vibetv.presentation.home.movie_grid

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.vibetv.common.utils.Resource
import com.vibetv.core.data.entities.PopularResultEntity
import com.vibetv.core.data.entities.TrendingEntity

class MovieGridModel(
    val title: String,
  //  nowPlaying: List<NowPlayingResultEntity>? = emptyList(),
    popular: List<PopularResultEntity>? = emptyList(),
    trending: List<TrendingEntity>? = emptyList(),
    error: Resource.Error<Unit>? = null,
) {
    var error: Resource.Error<Unit>? by mutableStateOf(error)
    //var nowPlaying: List<NowPlayingResultEntity>? by mutableStateOf(nowPlaying)
    var popular: List<PopularResultEntity>? by mutableStateOf(popular)
    var trending: List<TrendingEntity>? by mutableStateOf(trending)

}
