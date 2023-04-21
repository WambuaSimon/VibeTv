package com.vibetv.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vibetv.core.model.movie_response.MovieResults

@Entity(tableName = "trending")
data class TrendingEntity(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String? = null,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val title: String,
    val vote_average: Double,
    val genreId: List<Int>,
    val release_date: String? = null
){
    internal companion object{
        fun MovieResults.toTrendingEntity() = TrendingEntity(
            id = id,
            adult = adult,
            backdrop_path = backdrop_path,
            overview = overview,
            popularity = popularity,
            poster_path = poster_path,
            title = title,
            vote_average = vote_average,
            genreId = genre_ids,
            release_date = release_date
        )
    }
}
