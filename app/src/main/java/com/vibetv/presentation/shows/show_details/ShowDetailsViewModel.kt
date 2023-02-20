package com.vibetv.presentation.shows.show_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibetv.common.utils.Resource
import com.vibetv.common.utils.ViewState
import com.vibetv.core.repository.SeasonsRepository
import com.vibetv.core.repository.ShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailsViewModel @Inject constructor(
    repo: ShowRepository,
    val seasonsRepo: SeasonsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val model: ShowDetailsModel = ShowDetailsModel()
    val episodeModel: EpisodeModel = EpisodeModel()
    val seasonsModel: SeasonDetailsModel = SeasonDetailsModel()
    private val showId: Int = savedStateHandle.get<Int>("infoId")?.toInt()!!

    init {
        viewModelScope.launch {
            repo.getShowDetails(showId).map { details ->
                when (details) {
                    is Resource.Error -> {
                        ViewState.Ready(
                            Resource.Error<ShowDetailsPageState>(
                                details.throwable
                            )
                        )
                    }

                    Resource.Loading -> ViewState.Loading
                    is Resource.Success -> {
                        ViewState.Ready(
                            Resource.Success(
                                ShowDetailsPageState(showDetails = details.result)
                            )
                        )

                    }
                }

            }.collect { viewState ->
                model.state = viewState
                if (viewState is ViewState.Ready && viewState.value is Resource.Success) {
                    val result = viewState.value.result
                    ShowDetailsPageState(showDetails = result.showDetails)
                }
            }
        }
        getSeasonDetails(0)
    }

    fun getSeasonDetails(seasonNumber: Int) {
        viewModelScope.launch {
            seasonsRepo.getSeasonDetails(showId, seasonNumber).map { seasonsEntity ->
                when (seasonsEntity) {
                    is Resource.Error -> {
                        ViewState.Ready(
                            Resource.Error<SeasonsDetailsPageState>(
                                seasonsEntity.throwable
                            )
                        )
                    }

                    Resource.Loading -> ViewState.Loading

                    is Resource.Success -> {
                        ViewState.Ready(
                            Resource.Success(
                                SeasonsDetailsPageState(
                                    seasonDetails = seasonsEntity.result
                                )
                            )
                        )

                    }
                }
            }.collect { viewState ->
                seasonsModel.state = viewState
                if (viewState is ViewState.Ready && viewState.value is Resource.Success) {
                    val result = viewState.value.result
                    SeasonsDetailsPageState(seasonDetails = result.seasonDetails)
                }

            }
        }
    }

    fun getEpisode(seasonNumber: Int, episodeNumber: Int) {
        viewModelScope.launch {
            seasonsRepo.getEpisodeDetails(showId, seasonNumber, episodeNumber).map { episode ->
                when (episode) {
                    is Resource.Error -> {
                        ViewState.Ready(
                            Resource.Error<EpisodeModelPageState>(episode.throwable)
                        )
                    }

                    Resource.Loading -> ViewState.Loading
                    is Resource.Success -> {
                        ViewState.Ready(
                            Resource.Success(
                                EpisodeModelPageState(
                                    episode = episode.result
                                )
                            )
                        )
                    }

                }
            }.collect { viewState ->
                episodeModel.state = viewState
                if (viewState is ViewState.Ready && viewState.value is Resource.Success) {
                    val result = viewState.value.result
                    EpisodeModelPageState(result.episode)
                }
            }
        }
    }


}