package com.vibetv.core.model.season_details

import kotlinx.serialization.Serializable

@Serializable
data class GuestStar(
    val adult: Boolean,
    val character: String,
    val credit_id: String,
    val gender: Int?=null,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val order: Int,
    val original_name: String,
    val popularity: Double,
    val profile_path: String?=null
)