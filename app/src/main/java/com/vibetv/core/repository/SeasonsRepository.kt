package com.vibetv.core.repository

import android.content.Context
import com.vibetv.common.utils.Resource
import com.vibetv.core.api.VibeApi
import com.vibetv.core.data.dao.SeasonsDao
import com.vibetv.core.data.entities.season.EpisodeEntity
import com.vibetv.core.data.entities.season.EpisodeEntity.Companion.toEpisodeEntity
import com.vibetv.core.data.entities.season.SeasonDetailsEntity
import com.vibetv.core.data.entities.season.SeasonDetailsEntity.Companion.toEntity
import com.vibetv.core.network.networkBoundResource
import com.vibetv.core.network.networkStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeasonsRepository @Inject constructor(
    private val api: VibeApi,
    @ApplicationContext
    private val context: Context,
    private val dao: SeasonsDao,
) {
    fun getSeasonDetails(tvId: Int, seasonName: Int): Flow<Resource<SeasonDetailsEntity>> =
        networkBoundResource(
            fetch = { api.getSeasonDetails(tvId, seasonName) },
            networkStatus = context.networkStatus(),
            query = { dao.seasonDetails() },
            saveRemoteData = { response ->
                dao.replaceSeasonDetails(response.toEntity())
            }
        )

    fun getEpisodeDetails(
        tvId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): Flow<Resource<EpisodeEntity>> =
        networkBoundResource(
            fetch = { api.getEpisodeItem(tvId, seasonNumber, episodeNumber) },
            networkStatus = context.networkStatus(),
            query = { dao.getSeasonEpisodes(seasonNumber) },
            saveRemoteData = { response ->
                dao.replaceEpisodeDetails(response.toEpisodeEntity())

            }
        )

}