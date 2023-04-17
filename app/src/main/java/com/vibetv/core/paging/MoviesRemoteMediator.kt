package com.vibetv.core.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vibetv.core.data.AppDatabase
import com.vibetv.core.data.dao.MovieDao
import com.vibetv.core.data.entities.MovieRemoteKeyEntity
import com.vibetv.core.data.entities.NowPlayingResultEntity
import com.vibetv.core.data.entities.NowPlayingResultEntity.Companion.toNowPlayingEntity
import com.vibetv.core.repository.MovieRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator @Inject internal constructor(
    private val db: AppDatabase,
    private val movieDao: MovieDao,
    private val repository: MovieRepository,
) : RemoteMediator<Int, NowPlayingResultEntity>() {
    private val remoteKeyDao = db.movieRemoteKeyDao()
    /* override suspend fun initialize(): InitializeAction {
         val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
         return if (System.currentTimeMillis() - (db.movieRemoteKeyDao().getCreationTime()
                 ?: 0) < cacheTimeout
         ) {
             InitializeAction.SKIP_INITIAL_REFRESH
         } else {
             InitializeAction.LAUNCH_INITIAL_REFRESH
         }
     }*/

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NowPlayingResultEntity>
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
            val response = repository.getNowPlaying(pageIndex, state.config.pageSize)
            val nextPage =
                if (response.total_pages == 0 || pageIndex + 1 == response.total_pages) null else pageIndex + 1

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.delete()
                    movieDao.clearNowPlaying()
                }

                remoteKeyDao.insertOrReplace(MovieRemoteKeyEntity(nextPage = nextPage))

                db.movieDao()
                    .insertAll(response.results.map { it.toNowPlayingEntity() })
            }

            MediatorResult.Success(endOfPaginationReached = nextPage == null)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }

}