package com.vibetv.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibetv.common.utils.Resource
import com.vibetv.common.utils.ViewState
import com.vibetv.core.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    val repo: MovieRepository
) : ViewModel() {
    val allMoviesModel: AllMoviesModel = AllMoviesModel()
    val model: GenreModel = GenreModel()

    init {
        viewModelScope.launch {
            repo.getGenreList().map { genres ->
                when (genres) {
                    is Resource.Success -> {
                        ViewState.Ready(
                            Resource.Success(
                                GenreModelState(
                                    genreList = genres.result
                                )
                            )
                        )
                    }

                    is Resource.Error -> ViewState.Ready(Resource.Error<GenreModelState>(genres.throwable))
                    else -> ViewState.Loading
                }

            }.collect { viewState ->
                model.state = viewState
                if (viewState is ViewState.Ready && viewState.value is Resource.Success) {
                    val result = viewState.value.result
                    GenreModelState(genreList = result.genreList)
                }
                if (viewState is ViewState.Ready && viewState.value is Resource.Error) {
                    model.error = viewState.value.mapError { }
                }
            }

        }
        getAllMovies()
    }

    fun getAllMovies(genreId: Int?=null ) {
        viewModelScope.launch {
            repo.getMoviesByGenre(genreId).map { allMovies ->

                when (allMovies) {
                    is Resource.Success -> {
                        ViewState.Ready(
                            Resource.Success(
                                AllMoviesModelState(
                                    allMovies = allMovies.result
                                )
                            )
                        )
                    }

                    is Resource.Error -> ViewState.Ready(
                        Resource.Error<AllMoviesModelState>(
                            allMovies.throwable
                        )
                    )

                    Resource.Loading -> ViewState.Loading

                }

            }.collect { allMoviesViewState ->
                allMoviesModel.state = allMoviesViewState
                if (allMoviesViewState is ViewState.Ready && allMoviesViewState.value is Resource.Success) {
                    val result = allMoviesViewState.value.result.allMovies
                    AllMoviesModelState(allMovies = result)

                }
                if (allMoviesViewState is ViewState.Ready && allMoviesViewState.value is Resource.Error) {
                    allMoviesModel.error = allMoviesViewState.value.mapError { }
                }
            }
        }
    }


}








