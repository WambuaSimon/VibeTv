package com.vibetv.core.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vibetv.common.utils.Resource
import com.vibetv.core.api.VibeApi
import com.vibetv.core.data.AppDatabase
import com.vibetv.core.data.dao.MovieDao
import com.vibetv.core.data.entities.AllMoviesResult
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
import com.vibetv.core.data.entities.movie_details.MovieDetailsResponseEntity
import com.vibetv.core.data.entities.movie_details.MovieDetailsResponseEntity.Companion.toMovieDetailsEntity
import com.vibetv.core.network.networkBoundResource
import com.vibetv.core.network.networkStatus
import com.vibetv.core.paging.MoviesRemoteMediator
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: VibeApi,
    @ApplicationContext
    private val context: Context,
    private val dao: MovieDao,
    private val db: AppDatabase,
) {

    /*fun getNowPlaying(): Flow<Resource<List<NowPlayingResultEntity>>> = networkBoundResource(
        fetch = { api.getNowPlaying() },
        networkStatus = context.networkStatus(),
        query = { dao.nowPlaying() },
        saveRemoteData = { response ->
            val result = response.results.map { it.toNowPlayingEntity() }
            dao.replaceNowPlaying(result)
        }
    )*/

    @OptIn(ExperimentalPagingApi::class)
    fun getNowPlaying(): Flow<PagingData<NowPlayingResultEntity>> {
        val pagingSourceFactory = { dao.nowPlaying()}
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 10, initialLoadSize = 20),
            remoteMediator = MoviesRemoteMediator(
                api = api,
                db = db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
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

    fun getAllMovies(): Flow<Resource<List<AllMoviesResult>>> {
        return dao.allMovies().filterNotNull().map { Resource.Success(result = it) }
    }

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