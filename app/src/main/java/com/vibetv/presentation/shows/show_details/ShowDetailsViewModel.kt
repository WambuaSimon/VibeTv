package com.vibetv.presentation.shows.show_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibetv.common.utils.Resource
import com.vibetv.common.utils.ViewState
import com.vibetv.core.data.entities.season.SeasonDetailsEntity
import com.vibetv.core.data.entities.show_details.ShowDetailsResponseEntity
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

    private fun buildState(
        showDetails: ShowDetailsResponseEntity?
    ): ShowDetailsPageState = ShowDetailsPageState(
        showDetails = showDetails
    )

    init {
        viewModelScope.launch {
            repo.getShowDetails(showId).collect { details ->
                when (details) {
                    is Resource.Error -> {
                        model.error = details.mapError {
                            buildState(showDetails = details.result)
                        }

                    }

                    is Resource.Success -> {
                        model.state = Resource.Success(
                            buildState(showDetails = details.result)
                        )
                        getSeasonDetails(0)
                    }

                    else -> Resource.Loading

                }
            }

        }
    }

    private fun buildSeasonState(seasonsDetailsEntity: SeasonDetailsEntity?):
            SeasonsDetailsPageState = SeasonsDetailsPageState(
        seasonDetails = seasonsDetailsEntity
    )

    fun getSeasonDetails(seasonNumber: Int) {
        viewModelScope.launch {
            seasonsRepo.getSeasonDetails(showId, seasonNumber).collect { seasons ->

                when (seasons) {

                    is Resource.Error -> {
                        seasonsModel.error = seasons.mapError {
                            buildSeasonState(seasonsDetailsEntity = seasons.result)
                        }
                    }

                    is Resource.Success -> {
                        seasonsModel.state = Resource.Success(
                            buildSeasonState(seasonsDetailsEntity = seasons.result)
                        )
                    }

                    else -> Resource.Loading
                }
            }
        }
    }

    fun getEpisode(seasonNumber: Int, episodeNumber: Int) {
        viewModelScope.launch {
            seasonsRepo.getEpisodeDetails(showId, seasonNumber, episodeNumber)
                .map { episode ->
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