package com.vibetv.core.model.shows.show_details

import com.vibetv.core.model.shared_models.Genre
import com.vibetv.core.model.shared_models.ProductionCompany
import kotlinx.serialization.Serializable

@Serializable
data class ShowDetailsResponse(
    val adult: Boolean,
    val backdrop_path: String?=null,
    val created_by: List<CreatedBy>? = null,
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val languages: List<String>,
    val last_air_date: String,
    val last_episode_to_air: LastEpisodeToAir,
    val name: String,
    val networks: List<Network>,
    val next_episode_to_air: NextEpisodeToAir?=null,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val overview: String,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val seasons: List<Season>,
    //val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val vote_average: Double,
    val vote_count: Int
)