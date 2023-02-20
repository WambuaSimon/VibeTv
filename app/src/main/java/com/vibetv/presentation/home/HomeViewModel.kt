package com.vibetv.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibetv.common.utils.Resource
import com.vibetv.common.utils.ViewState
import com.vibetv.core.repository.MovieRepository
import com.vibetv.presentation.home.state.HomeModel
import com.vibetv.presentation.home.state.HomePageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repo: MovieRepository
) : ViewModel() {
    val model: HomeModel = HomeModel()
    val state:HomePageState = HomePageState()

    val getNowPlaying = repo.getNowPlaying()

    init {
        viewModelScope.launch {
            combine(
                repo.getNowPlaying(),
                repo.getTopRated(),
                repo.getPopular()
            ) { playing, top, popular ->
                when {
                  top is Resource.Success || popular is Resource.Success -> {

                        ViewState.Ready(
                            Resource.Success(
                              state.copy(

                                      topRated = top.result?.toList(),
                                      popular = popular.result?.toList()

                              )
                            )
                        )

                    }

                    top is Resource.Error ->
                        ViewState.Ready(Resource.Error<HomePageState>(top.throwable))

                    popular is Resource.Error ->
                        ViewState.Ready(Resource.Error(popular.throwable))

                    else -> ViewState.Loading

                }

            }.distinctUntilChanged()
                .collect { viewState ->
                model.state = viewState
                if (viewState is ViewState.Ready && viewState.value is Resource.Success) {
                    val result = viewState.value.result
                    HomePageState(
                        popular = result.popular,
                        topRated = result.topRated
                    )
                }

            }

        }
    }


}


