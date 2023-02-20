package com.vibetv.core.data.entities.show_details

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vibetv.core.model.shared_models.Genre
import com.vibetv.core.model.shared_models.ProductionCompany
import com.vibetv.core.model.shows.show_details.CreatedBy
import com.vibetv.core.model.shows.show_details.LastEpisodeToAir
import com.vibetv.core.model.shows.show_details.Network
import com.vibetv.core.model.shows.show_details.NextEpisodeToAir
import com.vibetv.core.model.shows.show_details.Season
import com.vibetv.core.model.shows.show_details.ShowDetailsResponse
import com.vibetv.core.model.shows.show_details.SpokenLanguage

@Entity(tableName = "show_details")
data class ShowDetailsResponseEntity(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String? = null,
    val created_by: List<CreatedBy>? = null,
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val genres: List<Genre>,
    val homepage: String,
    val languages: List<String>,
    val last_air_date: String,
    @Embedded
    val last_episode_to_air: LastEpisodeToAir,
    val name: String,
    val networks: List<Network>,
    @Embedded
    val next_episode_to_air: NextEpisodeToAir? = null,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val overview: String,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val seasons: List<Season>,
    val status: String,
    val vote_average: Double,
    val vote_count: Int,
) {
    internal companion object {
        fun ShowDetailsResponse.toEntity() = ShowDetailsResponseEntity(
            id = id,
            adult = adult,
            backdrop_path = backdrop_path,
            created_by = created_by,
            episode_run_time = episode_run_time,
            first_air_date = first_air_date,
            genres = genres,
            homepage = homepage,
            languages = languages,
            last_air_date = last_air_date,
            last_episode_to_air = last_episode_to_air,
            name = name,
            networks = networks,
            next_episode_to_air = next_episode_to_air,
            number_of_episodes = number_of_episodes,
            number_of_seasons = number_of_seasons,
            overview = overview,
            poster_path = poster_path,
            production_companies = production_companies,
            seasons = seasons,
            status = status,
            vote_average = vote_average,
            vote_count = vote_count
        )
    }
}
