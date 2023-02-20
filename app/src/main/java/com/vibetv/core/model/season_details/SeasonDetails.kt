package com.vibetv.core.model.season_details

import kotlinx.serialization.Serializable

@Serializable
data class SeasonDetails(
    val _id: String,
    val air_date: String,
    val episodes: List<Episode>,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String?=null,
    val season_number: Int
)