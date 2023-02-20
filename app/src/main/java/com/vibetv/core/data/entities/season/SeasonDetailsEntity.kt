package com.vibetv.core.data.entities.season

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vibetv.core.model.season_details.Episode
import com.vibetv.core.model.season_details.SeasonDetails

@Entity(tableName = "season_details")
data class SeasonDetailsEntity(
    @PrimaryKey
    val _id: String,
    val air_date: String,
    val episodes: List<Episode>,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String?=null,
    val season_number: Int
){
    internal companion object{
        fun SeasonDetails.toEntity() = SeasonDetailsEntity(
            _id = _id,
            air_date = air_date,
            episodes = episodes,
            id = id,
            name = name,
            overview = overview,
            poster_path = poster_path,
            season_number = season_number
        )
    }
}
