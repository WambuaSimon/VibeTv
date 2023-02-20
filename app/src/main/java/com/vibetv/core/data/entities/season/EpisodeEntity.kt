package com.vibetv.core.data.entities.season

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vibetv.core.model.season_details.Crew
import com.vibetv.core.model.season_details.Episode
import com.vibetv.core.model.season_details.GuestStar
import com.vibetv.core.model.shows.EpisodeItem

@Entity(tableName = "episode")
data class EpisodeEntity(
    @PrimaryKey
    val id: Int,
    val air_date: String? = null,
    val crew: List<Crew>,
    val episode_number: Int,
    val guest_stars: List<GuestStar>,
    val name: String,
    val overview: String,
    val production_code: String? = null,
    val runtime: Int? = null,
    val season_number: Int,
    val still_path: String? = null,
    val vote_average: Double,
    val vote_count: Int
) {
    internal companion object {
        fun EpisodeItem.toEpisodeEntity() = EpisodeEntity(
            id = id,
            air_date = air_date,
            crew = crew,
            episode_number = episode_number,
            guest_stars = guest_stars,
            name = name,
            overview = overview,
            production_code = production_code,
            runtime = runtime,
            season_number = season_number,
            still_path = still_path,
            vote_average = vote_average,
            vote_count = vote_count
        )
    }
}
