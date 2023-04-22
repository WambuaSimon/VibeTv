package com.vibetv.presentation.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibetv.common.utils.Resource
import com.vibetv.core.data.entities.shows.AiringTodayEntity
import com.vibetv.core.data.entities.shows.LatestShowEntity
import com.vibetv.core.data.entities.shows.PopularShowsEntity
import com.vibetv.core.data.entities.shows.TopRatedShowsEntity
import com.vibetv.core.repository.ShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    repo: ShowRepository
) : ViewModel() {
    val model: ShowModel = ShowModel()

    private val refresh = MutableStateFlow(0)

    val state: StateFlow<Resource<ShowPageState>> = refresh
        .flatMapLatest {
            combineTransform(
                repo.getAiringToday(),
                repo.getLatest(),
                repo.getPopular(),
                repo.getTopRated()
            ) { airing, latest, popular, top ->
                when {
                    airing is Resource.Success || latest is Resource.Success ||
                            popular is Resource.Success || top is Resource.Success -> {
                        this.emit(
                            Resource.Success(
                                buildState(
                                    airingToday = airing.result?.toList(),
                                    latest = latest.result?.toList(),
                                    popular = popular.result?.toList(),
                                    top = top.result?.toList()

                                )
                            )
                        )
                    }

                    airing is Resource.Error ->
                        model.error = airing.mapError {
                            buildState(
                                airingToday = airing.result,
                                latest = latest.result,
                                popular = popular.result,
                                top = top.result
                            )
                        }

                    latest is Resource.Error ->
                        model.error = latest.mapError {
                            buildState(
                                airingToday = airing.result,
                                latest = latest.result,
                                popular = popular.result,
                                top = top.result
                            )
                        }

                    popular is Resource.Error ->
                        model.error = popular.mapError {
                            buildState(
                                airingToday = airing.result,
                                latest = latest.result,
                                popular = popular.result,
                                top = top.result
                            )
                        }

                    top is Resource.Error ->
                        model.error = top.mapError {
                            buildState(
                                airingToday = airing.result,
                                latest = latest.result,
                                popular = popular.result,
                                top = top.result
                            )
                        }
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Resource.Loading)

    private fun buildState(
        airingToday: List<AiringTodayEntity>?,
        latest: List<LatestShowEntity>?,
        popular: List<PopularShowsEntity>?,
        top: List<TopRatedShowsEntity>?
    ): ShowPageState = ShowPageState(
        airingToday = airingToday,
        latestShow = latest,
        popularShow = popular,
        topRatedShow = top
    )

    fun refresh() {
        refresh.value++
    }

}