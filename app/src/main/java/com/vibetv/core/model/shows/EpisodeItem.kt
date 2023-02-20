package com.vibetv.core.model.shows

import com.vibetv.core.model.season_details.Crew
import com.vibetv.core.model.season_details.GuestStar
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeItem(
    val air_date: String?=null,
    val crew: List<Crew>,
    val episode_number: Int,
    val guest_stars: List<GuestStar>,
    val id: Int,
    val name: String,
    val overview: String,
    val production_code: String?=null,
    val runtime: Int?=null,
    val season_number: Int,
    val still_path: String?=null,
    val vote_average: Double,
    val vote_count: Int
)
