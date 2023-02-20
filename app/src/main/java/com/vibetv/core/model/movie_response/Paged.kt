package com.vibetv.core.model.movie_response

import kotlinx.serialization.Serializable

@Serializable
data class Paged<T>(
    val page: Int,
    val total_pages: Int,
    val results: T,
)
