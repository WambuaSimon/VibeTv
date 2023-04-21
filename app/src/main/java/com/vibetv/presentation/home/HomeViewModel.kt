package com.vibetv.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibetv.common.utils.Resource
import com.vibetv.core.data.entities.NowPlayingResultEntity
import com.vibetv.core.data.entities.PopularResultEntity
import com.vibetv.core.data.entities.TopRatedResultEntity
import com.vibetv.core.data.entities.TrendingEntity
import com.vibetv.core.repository.MovieRepository
import com.vibetv.presentation.home.state.HomeModel
import com.vibetv.presentation.home.state.HomePageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    repo: MovieRepository,
) : ViewModel() {
    val homeModel: HomeModel = HomeModel()

    private val refresh = MutableStateFlow(0)

    val state: StateFlow<Resource<HomePageState>> = refresh
        .flatMapLatest { homeModel.selectedFilterOption }
        .flatMapLatest {
            combineTransform(
                repo.getTopRated(),
                repo.getTrendingMovies(it.name),
                repo.getNowPlaying(),
                repo.getPopular(),

                ) { top, trending, playing, popular ->
                when {
                    top is Resource.Success || trending is Resource.Success || playing is Resource.Success || popular is Resource.Success -> {
                        this.emit(
                            Resource.Success(
                                buildState(
                                    topRated = top.result?.toList(),
                                    trending = trending.result?.toList(),
                                    nowPlaying = playing.result?.toList(),
                                    popular = popular.result?.toList(),
                                )
                            )
                        )
                    }

                    top is Resource.Error ->
                        homeModel.error = top.mapError {
                            buildState(
                                topRated = top.result,
                                trending = trending.result,
                                nowPlaying = playing.result,
                                popular = popular.result
                            )
                        }

                    trending is Resource.Error ->
                        homeModel.error = trending.mapError {
                            buildState(
                                topRated = top.result,
                                trending = trending.result,
                                nowPlaying = playing.result,
                                popular = popular.result
                            )
                        }

                    playing is Resource.Error ->
                        homeModel.error = playing.mapError {
                            buildState(
                                topRated = top.result,
                                trending = trending.result,
                                nowPlaying = playing.result,
                                popular = popular.result
                            )
                        }

                    popular is Resource.Error ->
                        homeModel.error = popular.mapError {
                            buildState(
                                topRated = top.result,
                                trending = trending.result,
                                nowPlaying = playing.result,
                                popular = popular.result
                            )
                        }

                    else -> Resource.Loading
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Resource.Loading)

    private fun buildState(
        topRated: List<TopRatedResultEntity>?,
        trending: List<TrendingEntity>?,
        nowPlaying: List<NowPlayingResultEntity>?,
        popular: List<PopularResultEntity>?
    ): HomePageState = HomePageState(
        nowPlaying = nowPlaying,
        popular = popular,
        topRated = topRated,
        trending = trending

    )

    fun refresh() {
        refresh.value++
    }
}






