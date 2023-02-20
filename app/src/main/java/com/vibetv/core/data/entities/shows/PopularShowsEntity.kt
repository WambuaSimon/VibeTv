package com.vibetv.core.data.entities.shows

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vibetv.core.model.shows.ShowResults

@Entity(tableName = "popular_shows")
data class PopularShowsEntity(
    @PrimaryKey
    val id: Int,
    val poster_path:String?=null,
    val vote_average: Double,
){
    internal companion object{
        fun ShowResults.toPopularEntity() = PopularShowsEntity(
            id=id,
            vote_average = vote_average,
            poster_path = poster_path
        )
    }
}
