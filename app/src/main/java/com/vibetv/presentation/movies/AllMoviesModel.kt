package com.vibetv.presentation.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.vibetv.common.utils.Resource
import com.vibetv.core.data.entities.MovieByGenreEntity

class AllMoviesModel (
    state: Resource<AllMoviesState> = Resource.Loading,
    error: Resource.Error<Unit>? = null,
){
    var state by mutableStateOf(state)
    var error: Resource.Error<Unit>? by mutableStateOf(error)
}

data class AllMoviesState(
    var allMovies: List<MovieByGenreEntity>? = emptyList(),
)


