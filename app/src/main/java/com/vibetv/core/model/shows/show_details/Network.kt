package com.vibetv.core.model.shows.show_details

import kotlinx.serialization.Serializable

@Serializable
data class Network(
    val id: Int,
    val logo_path: String?=null,
    val name: String,
    val origin_country: String
)