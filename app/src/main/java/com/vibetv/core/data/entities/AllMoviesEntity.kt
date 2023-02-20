package com.vibetv.core.data.entities


data class AllMoviesResult(
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val title: String,
    val vote_average: Double,
    val genre: List<Int>,
    val release_date:String
)
