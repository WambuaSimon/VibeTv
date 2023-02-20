package com.vibetv.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vibetv.core.model.movie_response.MovieResults

@Entity(tableName = "popular")
data class PopularResultEntity(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String?=null,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val title: String,
    val vote_average: Double,
    @ColumnInfo(defaultValue = "")
    val genreId: List<Int>,
    @ColumnInfo(defaultValue = "")
    val release_date: String
){
    internal companion object {
        fun MovieResults.toPopularEntity() = PopularResultEntity(
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
