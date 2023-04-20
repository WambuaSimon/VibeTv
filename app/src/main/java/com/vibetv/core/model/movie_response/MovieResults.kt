package com.vibetv.core.model.movie_response

import kotlinx.serialization.Serializable

@Serializable
data class MovieResults(
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String?=null,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val title: String,
    val vote_average: Double,
    val genre_ids: List<Int>,
    val release_date: String?=null
)
