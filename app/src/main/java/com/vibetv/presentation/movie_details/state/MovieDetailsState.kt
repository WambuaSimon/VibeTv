package com.vibetv.presentation.movie_details.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.vibetv.common.utils.Resource
import com.vibetv.common.utils.ViewState
import com.vibetv.core.data.entities.movie_details.MovieDetailsResponseEntity

class MovieDetailsModel(
    state: ViewState<Resource<MovieDetailsPageState>> = ViewState.Empty,
    error: Resource.Error<Unit>? = null,
) {
    var state by mutableStateOf(state)
    var error: Resource.Error<Unit>? by mutableStateOf(error)
}

data class MovieDetailsPageState(
    var movieDetails: MovieDetailsResponseEntity? = null
)