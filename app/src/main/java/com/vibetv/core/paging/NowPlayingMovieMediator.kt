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

@OptIn(ExperimentalPagingApi::class)
class NowPlayingMovieMediator(
    private val api: VibeApi,
    private val db: AppDatabase
) : RemoteMediator<Int, NowPlayingResultEntity>() {

    val movieDao = db.movieDao()
    val remoteKeyDao = db.movieRemoteKeyDao()
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NowPlayingResultEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextPage
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

            }
        }

        try {
            val apiResponse = api.getNowPlaying(page = page)
            val movies = apiResponse.results
            val endOfPaginationReached = movies.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.delete()
                    movieDao.clearNowPlaying()
                }

                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = movies.map {
                    MovieRemoteKeyEntity(
                        id = it.id,
                        prevKey = prevKey,
                        currentPage = page,
                        nextPage = nextKey
                    )
                }
                db.movieRemoteKeyDao().insertOrReplace(remoteKeys)
                db.movieDao().insertAll(
                    movies.map { result -> result.toNowPlayingEntity() }
                        .onEachIndexed { _, nowPlayingResultEntity ->
                            nowPlayingResultEntity.page = page
                        },
                )
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }

    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, NowPlayingResultEntity>): MovieRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                db.movieRemoteKeyDao().remoteKeyByMovieId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, NowPlayingResultEntity>): MovieRemoteKeyEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            db.movieRemoteKeyDao().remoteKeyByMovieId(movie.id)
        }
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, NowPlayingResultEntity>): MovieRemoteKeyEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            db.movieRemoteKeyDao().remoteKeyByMovieId(movie.id)
        }
    }
}