package com.vibetv.core.model.genre

import kotlinx.serialization.Serializable

@Serializable
data class GenreList(
    val id: Int?=null,
    val name: String? = null
)