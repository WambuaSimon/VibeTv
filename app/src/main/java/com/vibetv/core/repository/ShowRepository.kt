package com.vibetv.core.repository

import android.content.Context
import com.vibetv.common.utils.Resource
import com.vibetv.core.api.VibeApi
import com.vibetv.core.data.dao.ShowDao
import com.vibetv.core.data.entities.show_details.ShowDetailsResponseEntity
import com.vibetv.core.data.entities.show_details.ShowDetailsResponseEntity.Companion.toEntity
import com.vibetv.core.data.entities.shows.AiringTodayEntity
import com.vibetv.core.data.entities.shows.AiringTodayEntity.Companion.toAiringEntity
import com.vibetv.core.data.entities.shows.LatestShowEntity
import com.vibetv.core.data.entities.shows.LatestShowEntity.Companion.toLatestEntity
import com.vibetv.core.data.entities.shows.PopularShowsEntity
import com.vibetv.core.data.entities.shows.PopularShowsEntity.Companion.toPopularEntity
import com.vibetv.core.data.entities.shows.TopRatedShowsEntity
import com.vibetv.core.data.entities.shows.TopRatedShowsEntity.Companion.toTopRatedEntity
import com.vibetv.core.network.networkBoundResource
import com.vibetv.core.network.networkStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowRepository @Inject constructor(
    private val api: VibeApi,
    @ApplicationContext
    private val context: Context,
    private val dao: ShowDao
) {
    fun getAiringToday(): Flow<Resource<List<AiringTodayEntity>>> = networkBoundResource(
        fetch = { api.getShowsAiringToday() },
        networkStatus = context.networkStatus(),
        query = { dao.airingToday() },
        saveRemoteData = { response ->
            val result = response.results.map { it.toAiringEntity() }
            dao.replaceAiringToday(result)

        }
    )

    fun getLatest(): Flow<Resource<List<LatestShowEntity>>> = networkBoundResource(
        fetch = { api.getLatestShows() },
        networkStatus = context.networkStatus(),
        query = { dao.latestShow() },
        saveRemoteData = { response ->
            val result = response.results.map { it.toLatestEntity() }
            dao.replaceLatestShow(result)

        }
    )

    fun getPopular(): Flow<Resource<List<PopularShowsEntity>>> = networkBoundResource(
        fetch = { api.getPopularShows() },
        networkStatus = context.networkStatus(),
        query = { dao.popularShow() },
        saveRemoteData = { response ->
            val result = response.results.map { it.toPopularEntity() }
            dao.replacePopularShow(result)

        }
    )

    fun getTopRated(): Flow<Resource<List<TopRatedShowsEntity>>> = networkBoundResource(
        fetch = { api.getTopRatedShows() },
        networkStatus = context.networkStatus(),
        query = { dao.topRatedShow() },
        saveRemoteData = { response ->
            val result = response.results.map { it.toTopRatedEntity() }
            dao.replaceTopRatedShow(result)

        }
    )

    fun getShowDetails(id:Int): Flow<Resource<ShowDetailsResponseEntity>> = networkBoundResource(
        fetch = {api.getShowDetails(id)},
        networkStatus = context.networkStatus(),
        query = {dao.showDetails(id)},
        saveRemoteData = {
            dao.insertShowDetails(it.toEntity())
        }
    )
}