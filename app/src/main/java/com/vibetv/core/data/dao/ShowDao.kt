package com.vibetv.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.vibetv.core.data.entities.show_details.ShowDetailsResponseEntity
import com.vibetv.core.data.entities.shows.AiringTodayEntity
import com.vibetv.core.data.entities.shows.LatestShowEntity
import com.vibetv.core.data.entities.shows.PopularShowsEntity
import com.vibetv.core.data.entities.shows.TopRatedShowsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowDao {
    //Airing today
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAiringToday(airingToday: List<AiringTodayEntity>)

    @Query("SELECT * FROM airing_today")
    fun airingToday(): Flow<List<AiringTodayEntity>>

    @Query("DELETE FROM airing_today")
    suspend fun clearAiringToday()

    @Transaction
    suspend fun replaceAiringToday(airingToday: List<AiringTodayEntity>) {
        clearAiringToday()
        insertAiringToday(airingToday)
    }

    //latest show
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLatestShow(latestShow: List<LatestShowEntity>)

    @Query("SELECT * FROM airing_today")
    fun latestShow(): Flow<List<LatestShowEntity>>

    @Query("DELETE FROM airing_today")
    suspend fun clearLatestShow()

    @Transaction
    suspend fun replaceLatestShow(latestShow: List<LatestShowEntity>) {
        clearLatestShow()
        insertLatestShow(latestShow)
    }

    //popular show
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularShow(popularShow: List<PopularShowsEntity>)

    @Query("SELECT * FROM popular_shows")
    fun popularShow(): Flow<List<PopularShowsEntity>>

    @Query("DELETE FROM popular_shows")
    suspend fun clearPopularShow()

    @Transaction
    suspend fun replacePopularShow(popularShow: List<PopularShowsEntity>) {
        clearPopularShow()
        insertPopularShow(popularShow)
    }

    //top rated show
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopRated(popularShow: List<TopRatedShowsEntity>)

    @Query("SELECT * FROM top_rated_shows")
    fun topRatedShow(): Flow<List<TopRatedShowsEntity>>

    @Query("DELETE FROM top_rated_shows")
    suspend fun clearTopRated()

    @Transaction
    suspend fun replaceTopRatedShow(topRatedShow: List<TopRatedShowsEntity>) {
        clearTopRated()
        insertTopRated(topRatedShow)
    }

    //show details
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShowDetails(showDetails: ShowDetailsResponseEntity)

    @Query("SELECT *  FROM show_details WHERE :id = id")
    fun showDetails(id: Int): Flow<ShowDetailsResponseEntity>

    @Query("DELETE from show_details")
    suspend fun clearShowDetails()

    @Transaction
    suspend fun replaceShowDetails(showDetails: ShowDetailsResponseEntity) {
        clearShowDetails()
        insertShowDetails(showDetails)
    }
}