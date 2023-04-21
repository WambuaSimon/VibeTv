package com.vibetv.core.repository

import android.content.Context
import com.vibetv.common.utils.Resource
import com.vibetv.core.api.VibeApi
import com.vibetv.core.data.dao.MovieDao
import com.vibetv.core.data.entities.GenreListEntity
import com.vibetv.core.data.entities.GenreListEntity.Companion.toGenreEntity
import com.vibetv.core.data.entities.MovieByGenreEntity
import com.vibetv.core.data.entities.MovieByGenreEntity.Companion.toEntity
import com.vibetv.core.data.entities.NowPlayingResultEntity
import com.vibetv.core.data.entities.NowPlayingResultEntity.Companion.toNowPlayingEntity
import com.vibetv.core.data.entities.PopularResultEntity
import com.vibetv.core.data.entities.PopularResultEntity.Companion.toPopularEntity
import com.vibetv.core.data.entities.TopRatedResultEntity
import com.vibetv.core.data.entities.TopRatedResultEntity.Companion.toTopRatedEntity
import com.vibetv.core.data.entities.TrendingEntity
import com.vibetv.core.data.entities.TrendingEntity.Companion.toTrendingEntity
import com.vibetv.core.data.entities.movie_details.MovieDetailsResponseEntity
import com.vibetv.core.data.entities.movie_details.MovieDetailsResponseEntity.Companion.toMovieDetailsEntity
import com.vibetv.core.network.NetworkResponse
import com.vibetv.core.network.networkBoundResource
import com.vibetv.core.network.networkStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: VibeApi,
    @ApplicationContext
    private val context: Context,
    private val dao: MovieDao,
) {
    fun getNowPlaying(): Flow<Resource<List<NowPlayingResultEntity>>> {
        return networkBoundResource(
            query = { dao.nowPlayingHome() },
            fetch = {
                runCatching {
                    api.nowPlaying()
                }.fold(
                    onSuccess = { NetworkResponse.Success(it) },
                    onFailure = { NetworkResponse.Error.UnknownError(it) }
                )


            },
            networkStatus = context.networkStatus(),

            saveRemoteData = { response ->
                val nowPlayingResult = response.results.map { it.toNowPlayingEntity() }
                dao.replaceNowPlaying(nowPlayingResult)
            }
        )

    }

    fun getTrendingMovies(
        timeWindow: String
    ): Flow<Resource<List<TrendingEntity>>> {
        return networkBoundResource(
            query = { dao.getTrending() },
            fetch = {
                runCatching {
                    api.getTrending(timeWindow)
                }.fold(
                    onSuccess = { NetworkResponse.Success(it) },
                    onFailure = { NetworkResponse.Error.UnknownError(it) }
                )
            },
            networkStatus = context.networkStatus(),

            saveRemoteData = { response ->
                val trending = response.results.map { it.toTrendingEntity() }
                dao.replaceTrending(trending)

            }
        )
    }

    fun getTopRated(): Flow<Resource<List<TopRatedResultEntity>>> = networkBoundResource(
        fetch = { api.getTopRated() },
        networkStatus = context.networkStatus(),
        query = { dao.topRated() },
        saveRemoteData = { response ->
            val result = response.results.map { it.toTopRatedEntity() }
            dao.replaceTopRated(result)
        }
    )

    fun getPopular(): Flow<Resource<List<PopularResultEntity>>> = networkBoundResource(
        fetch = { api.getPopularMovies() },
        networkStatus = context.networkStatus(),
        query = { dao.popular() },
        saveRemoteData = { response ->
            val result = response.results.map { it.toPopularEntity() }
            dao.replacePopular(result)
        }
    )

    fun getMovieDetails(id: Int): Flow<Resource<MovieDetailsResponseEntity>> = networkBoundResource(
        fetch = { api.getMovieDetails(id) },
        networkStatus = context.networkStatus(),
        query = { dao.movieDetails(id) },
        saveRemoteData = {
            dao.replaceMovieDetails(it.toMovieDetailsEntity())
        }
    )

    fun getGenreList(): Flow<Resource<List<GenreListEntity>>> = networkBoundResource(
        fetch = { api.getGenreList() },
        networkStatus = context.networkStatus(),
        query = { dao.genreList() },
        saveRemoteData = { genreList ->
            dao.replaceGenreList(genreList.genreList.map { it.toGenreEntity() })
        }
    )

    fun getMoviesByGenre(genreId: Int?): Flow<Resource<List<MovieByGenreEntity>>> =
        networkBoundResource(
            fetch = { if (genreId == null) api.getMovieByGenre() else api.getMovieByGenre(genreId) },
            networkStatus = context.networkStatus(),
            query = { dao.movieByGenre() },
            saveRemoteData = { response ->
                val result = response.results.map { it.toEntity() }
                dao.replaceMovieByGenre(result)
            }
        )

}