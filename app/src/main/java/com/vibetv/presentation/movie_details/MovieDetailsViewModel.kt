package com.vibetv.presentation.movie_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibetv.common.utils.Resource
import com.vibetv.common.utils.ViewState
import com.vibetv.core.data.entities.movie_details.MovieDetailsResponseEntity
import com.vibetv.core.repository.MovieRepository
import com.vibetv.presentation.home.state.HomePageState
import com.vibetv.presentation.movie_details.state.MovieDetailsModel
import com.vibetv.presentation.movie_details.state.MovieDetailsPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repo: MovieRepository
) : ViewModel() {
    val model: MovieDetailsModel = MovieDetailsModel()
    private val movieId: Int = savedStateHandle.get<Int>("id")?.toInt()!!

    init{
        viewModelScope.launch {
            repo.getMovieDetails(movieId).map { details ->
                when (details) {
                    is Resource.Success -> {

                        ViewState.Ready(
                            Resource.Success(
                                MovieDetailsPageState(
                                    movieDetails = details.result
                                )
                            )
                        )
                    }

                    is Resource.Error -> ViewState.Ready(Resource.Error<MovieDetailsPageState>(details.throwable))
                    else -> ViewState.Loading
                }

            }.collect{ viewState ->
                model.state = viewState
                if(viewState is ViewState.Ready && viewState.value is Resource.Success){
                    val result = viewState.value.result
                    MovieDetailsPageState(movieDetails = result.movieDetails)
                }
            }
        }
    }

}


