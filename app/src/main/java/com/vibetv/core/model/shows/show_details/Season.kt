package com.vibetv.core.model.shows.show_details

import kotlinx.serialization.Serializable

@Serializable
data class Season(
    val air_date: String?=null,
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String?=null,
    val season_number: Int
)