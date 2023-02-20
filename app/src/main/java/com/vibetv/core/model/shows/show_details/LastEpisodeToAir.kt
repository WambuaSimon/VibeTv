package com.vibetv.core.model.shows.show_details

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class LastEpisodeToAir(
    @ColumnInfo(name= "last_episode_air_date")
    val air_date: String?=null,
    @ColumnInfo(name= "last_episode_number")
    val episode_number: Int,
    @ColumnInfo(name= "last_episode_id")
    val id: Int,
    @ColumnInfo(name= "last_episode_name")
    val name: String,
    @ColumnInfo(name= "last_episode_overview")
    val overview: String,
    @ColumnInfo(name= "last_episode_production_code")
    val production_code: String,
    @ColumnInfo(name= "last_episode_runtime")
    val runtime: Int?=null,
    @ColumnInfo(name= "last_episode_season_number")
    val season_number: Int,
    @ColumnInfo(name= "last_episode_show_id")
    val show_id: Int,
    @ColumnInfo(name= "last_episode_still_path")
    val still_path: String?=null,
    @ColumnInfo(name= "last_episode_vote_average")
    val vote_average: Double,
    @ColumnInfo(name= "last_episode_vote_count")
    val vote_count: Int
)