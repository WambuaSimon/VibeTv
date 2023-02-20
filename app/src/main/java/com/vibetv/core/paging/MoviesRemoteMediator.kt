package com.vibetv.core.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vibetv.core.api.VibeApi
import com.vibetv.core.data.AppDatabase
import com.vibetv.core.data.entities.MovieRemoteKeyEntity
import com.vibetv.core.data.entities.NowPlayingResultEntity
import com.vibetv.core.data.entities.NowPlayingResultEntity.Companion.toNowPlayingEntity
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator @Inject internal constructor(
    private val db: AppDatabase,
    private val api: VibeApi
) : RemoteMediator<Int, NowPlayingResultEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        return if (System.currentTimeMillis() - (db.movieRemoteKeyDao().getCreationTime()
                ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NowPlayingResultEntity>
    ): MediatorResult {

            val pageIndex = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                    prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                    nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

                }

            }
        return try {
            val response = api.getNowPlaying(pageIndex)
            val movies = response.results.map { it.toNowPlayingEntity() }
            val endOfPaginationReached = movies.isEmpty()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.movieRemoteKeyDao().delete()
                    db.movieDao().clearNowPlaying()
                }

                val prevKey = if (pageIndex > 1) pageIndex - 1 else null
                val nextKey = if (endOfPaginationReached) null else pageIndex + 1
                val remoteKeys = movies.map {
                    MovieRemoteKeyEntity(
                        movieID = it.id,
                        prevKey = prevKey,
                        currentPage = pageIndex,
                        nextKey = nextKey
                    )
                }
                db.movieRemoteKeyDao().insertOrReplace(remoteKeys)
                db.movieDao()
                    .insertNowPlaying(movies.onEachIndexed { _, movie -> movie.page = pageIndex })
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, NowPlayingResultEntity>): MovieRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                db.movieRemoteKeyDao().getRemoteKeyByMovieID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, NowPlayingResultEntity>): MovieRemoteKeyEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            db.movieRemoteKeyDao().getRemoteKeyByMovieID(movie.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, NowPlayingResultEntity>): MovieRemoteKeyEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            db.movieRemoteKeyDao().getRemoteKeyByMovieID(movie.id)
        }
    }

}