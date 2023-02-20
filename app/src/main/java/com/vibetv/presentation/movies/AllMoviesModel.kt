package com.vibetv.presentation.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.vibetv.common.utils.Resource
import com.vibetv.common.utils.ViewState
import com.vibetv.core.data.entities.AllMoviesResult
import com.vibetv.core.data.entities.MovieByGenreEntity

class AllMoviesModel (
    state: ViewState<Resource<AllMoviesModelState>> = ViewState.Empty,
    error: Resource.Error<Unit>? = null,
){
    var state by mutableStateOf(state)
    var error: Resource.Error<Unit>? by mutableStateOf(error)
}

data class AllMoviesModelState(
    var allMovies: List<MovieByGenreEntity>? = null,
)


