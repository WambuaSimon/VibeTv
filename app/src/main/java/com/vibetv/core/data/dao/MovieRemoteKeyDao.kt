package com.vibetv.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vibetv.core.data.entities.MovieRemoteKeyEntity

@Dao
interface MovieRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: List<MovieRemoteKeyEntity>)

    @Query("SELECT * FROM movie_remote_keys")
    suspend fun remoteKey(): MovieRemoteKeyEntity?

    @Query("SELECT * FROM movie_remote_keys WHERE movie_id = :id")
    suspend fun getRemoteKeyByMovieID(id:Int): MovieRemoteKeyEntity?

    @Query("SELECT created_at FROM movie_remote_keys Order By created_at DESC LIMIT 1")
   suspend fun getCreationTime():Long?

    @Query("DELETE FROM movie_remote_keys")
    suspend fun delete()

}