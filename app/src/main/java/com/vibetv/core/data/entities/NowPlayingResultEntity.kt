package com.vibetv.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vibetv.core.model.movie_response.MovieResults

@Entity(tableName = "now_playing")
data class NowPlayingResultEntity(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String?=null,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val title: String,
    val vote_average: Double,
    @ColumnInfo(defaultValue = "")
    val genre: List<Int>,
    @ColumnInfo(defaultValue = "")
    val release_date: String?=null,
    @ColumnInfo(name = "page")
    var page: Int?=null,
) {
    internal companion object {
        fun MovieResults.toNowPlayingEntity() = NowPlayingResultEntity(
            id = id,
            adult = adult,
            backdrop_path = backdrop_path,
            overview = overview,
            popularity = popularity,
            poster_path = poster_path,
            title = title,
            vote_average = vote_average,
            genre = genre_ids,
            release_date = release_date
        )
    }
}
