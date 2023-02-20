package com.vibetv.core.model.shows.show_details

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class NextEpisodeToAir(
    @ColumnInfo(name= "next_episode_air_date")
    val air_date: String?=null,
    val episode_number: Int,
    @ColumnInfo(name= "next_episode_id")
    val id: Int,
    @ColumnInfo(name= "next_episode_name")
    val name: String,
    @ColumnInfo(name= "next_episode_overview")
    val overview: String,
    @ColumnInfo(name= "next_episode_production_code")
    val production_code: String,
    @ColumnInfo(name= "next_episode_runtime")
    val runtime: Int?=null,
    @ColumnInfo(name= "next_episode_season_number")
    val season_number: Int,
    @ColumnInfo(name= "next_episode_show_id")
    val show_id: Int,
    @ColumnInfo(name= "next_episode_still_path")
    val still_path: String?=null,
    @ColumnInfo(name= "next_episode_vote_average")
    val vote_average: Double,
    @ColumnInfo(name= "next_episode_vote_count")
    val vote_count: Int
)