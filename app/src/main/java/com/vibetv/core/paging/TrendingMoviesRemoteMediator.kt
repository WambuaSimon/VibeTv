/*
package com.vibetv.core.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vibetv.core.api.VibeApi
import com.vibetv.core.data.AppDatabase
import com.vibetv.core.data.dao.MovieDao
import com.vibetv.core.data.entities.MovieRemoteKeyEntity
import com.vibetv.core.data.entities.TrendingEntity
import com.vibetv.core.data.entities.TrendingEntity.Companion.toTrendingEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class TrendingMoviesRemoteMediator @Inject internal constructor(
    private val db: AppDatabase,
    private val movieDao: MovieDao,
    private val api: VibeApi,
) : RemoteMediator<Int, TrendingEntity>() {
    private val remoteKeyDao = db.movieRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TrendingEntity>
    ): MediatorResult {
        return try {
            val pageIndex: Int = when (loadType) {
                LoadType.REFRESH -> 1

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)

                LoadType.APPEND -> {
                    remoteKeyDao.remoteKey()?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                }

            }

            val response = api.getTrendingMovies(mediaType = "movie", timeWindow = "day")

            val trendingMovies = response.results.map { it.toTrendingEntity() }

              val nextPage =
                if (response.total_pages == 1 || pageIndex + 1 == response.total_pages) null else pageIndex + 1

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.delete()
                    movieDao.clearTrending()
                }

                remoteKeyDao.insertOrReplace(MovieRemoteKeyEntity(nextPage = nextPage))

                db.movieDao()
                    .insertReplace(trendingMovies)
            }

            MediatorResult.Success(endOfPaginationReached = nextPage == null)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }

}*/
