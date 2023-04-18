package com.vibetv.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibetv.common.utils.Resource
import com.vibetv.core.data.entities.GenreListEntity
import com.vibetv.core.data.entities.MovieByGenreEntity
import com.vibetv.core.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    val repo: MovieRepository
) : ViewModel() {
    val allMoviesModel: AllMoviesModel = AllMoviesModel()

    val genreState: StateFlow<Resource<GenreState>> =
        repo.getGenreList().map { genres ->

            when (genres) {
                is Resource.Success ->
                    Resource.Success(
                        buildState(genres)
                    )

                is Resource.Error ->
                    genres.mapError { buildState(genres) }

                else -> Resource.Loading
            }

        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Resource.Loading)

    private fun buildState(
        genreListEntity: Resource<List<GenreListEntity>>
    ): GenreState = GenreState(
        genreList = genreListEntity.result
    )

    private fun buildMoviesState(
        allMoviesState: List<MovieByGenreEntity>
    ): AllMoviesState = AllMoviesState(
        allMovies = allMoviesState
    )

    init {
        getAllMovies()
    }

    fun getAllMovies(genreId: Int? = null) {
        viewModelScope.launch {
            repo.getMoviesByGenre(genreId).map { allMovies ->

                when (allMovies) {
                    is Resource.Success -> {
                        Resource.Success(
                            buildMoviesState(allMovies.result.toList())
                        )

                    }

                    is Resource.Error ->
                        allMovies.mapError {
                            buildMoviesState(
                                allMovies.result?.toList().orEmpty()
                            )
                        }

                    else -> Resource.Loading

                }

            }.collect { allMoviesViewState ->
                allMoviesModel.state = allMoviesViewState
                if (allMoviesViewState is Resource.Success) {
                    val result = allMoviesViewState.result.allMovies
                    AllMoviesState(allMovies = result)

                }
                if (allMoviesViewState is Resource.Error) {
                    allMoviesModel.error = allMoviesViewState.mapError {
                        val result = allMoviesViewState.result?.allMovies.orEmpty()
                        AllMoviesState(allMovies = result)
                        buildMoviesState(
                            result.toList()
                        )
                    }
                }
            }
        }
    }


}








