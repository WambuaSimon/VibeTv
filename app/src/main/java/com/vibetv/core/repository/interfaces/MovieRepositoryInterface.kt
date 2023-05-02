package com.vibetv.core.repository.interfaces

import com.vibetv.common.utils.Resource
import com.vibetv.core.data.entities.GenreListEntity
import com.vibetv.core.data.entities.MovieByGenreEntity
import com.vibetv.core.data.entities.PopularResultEntity
import com.vibetv.core.data.entities.TopRatedResultEntity
import com.vibetv.core.data.entities.TrendingEntity
import com.vibetv.core.data.entities.movie_details.MovieDetailsResponseEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepositoryInterface {

    fun getTrendingMovies(timeWindow: String): Flow<Resource<List<TrendingEntity>>>

    fun getTopRated(): Flow<Resource<List<TopRatedResultEntity>>>

    fun getPopularMovies():  Flow<Resource<List<PopularResultEntity>>>

    fun getMovieDetails(): Flow<Resource<MovieDetailsResponseEntity>>

    fun getGenreList(): Flow<Resource<List<GenreListEntity>>>

    fun getMovieByGenre(genreId: Int?): Flow<Resource<List<MovieByGenreEntity>>>
}