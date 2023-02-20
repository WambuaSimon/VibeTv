package com.vibetv.core.model.shows

import kotlinx.serialization.Serializable

@Serializable
data class ShowResults(
    val poster_path:String?=null,
    val vote_average: Double,
    val id: Int
)
