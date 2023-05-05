package com.vibetv.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.vibetv.core.data.entities.season.EpisodeEntity
import com.vibetv.core.data.entities.season.SeasonDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeasonsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasonDetails(seasonDetails: SeasonDetailsEntity)

    @Query("SELECT * FROM season_details")
    fun seasonDetails(): Flow<SeasonDetailsEntity>

    @Query("DELETE FROM season_details")
    suspend fun clearSeasonDetails()


    @Transaction
    suspend fun replaceSeasonDetails(seasonDetails: SeasonDetailsEntity) {
        clearSeasonDetails()
        insertSeasonDetails(seasonDetails)
    }

    //Episode
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodeDetails(episode: EpisodeEntity)


    @Query("SELECT * FROM episode")
    fun episode(): Flow<EpisodeEntity>

    @Query("DELETE FROM episode")
    suspend fun clearEpisodeDetails()

    @Query("SELECT * FROM episode WHERE season_number = :seasonNumber")
    fun getSeasonEpisodes(seasonNumber: Int): Flow<EpisodeEntity>

    @Transaction
    suspend fun replaceEpisodeDetails(episode: EpisodeEntity) {
        clearEpisodeDetails()
        insertEpisodeDetails(episode)
    }

}