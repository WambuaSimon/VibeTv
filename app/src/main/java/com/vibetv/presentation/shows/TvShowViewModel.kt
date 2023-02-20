package com.vibetv.presentation.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibetv.common.utils.Resource
import com.vibetv.common.utils.ViewState
import com.vibetv.core.repository.ShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    repo: ShowRepository
) : ViewModel() {
    val model: ShowModel = ShowModel()

    init {
        viewModelScope.launch {
            combine(
                repo.getAiringToday(),
                repo.getLatest(),
                repo.getPopular(),
                repo.getTopRated()
            ) { airing, latest, popular, top ->
                when {
                    airing is Resource.Success || latest is Resource.Success ||
                            popular is Resource.Success || top is Resource.Success -> {

                        ViewState.Ready(
                            Resource.Success(
                                ShowPageState(
                                    airingToday = airing.result?.toList(),
                                    latestShow = latest.result?.toList(),
                                    popularShow = popular.result?.toList(),
                                    topRatedShow = top.result?.toList()
                                )
                            )
                        )
                    }

                    airing is Resource.Error ->
                        ViewState.Ready(Resource.Error(airing.throwable))

                    latest is Resource.Error ->
                        ViewState.Ready(Resource.Error(latest.throwable))

                    popular is Resource.Error ->
                        ViewState.Ready(Resource.Error(popular.throwable))

                    top is Resource.Error ->
                        ViewState.Ready(Resource.Error<ShowPageState>(top.throwable))

                    else -> ViewState.Loading
                }
            }.collect { viewState ->
                model.state = viewState
                if (viewState is ViewState.Ready && viewState.value is Resource.Success) {
                    val result = viewState.value.result
                    ShowPageState(
                        airingToday = result.airingToday,
                        latestShow = result.latestShow,
                        popularShow = result.popularShow,
                        topRatedShow = result.topRatedShow
                    )
                }

            }
        }
    }
}