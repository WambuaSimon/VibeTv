package com.vibetv.core.model.shared_models

import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompany(
    val id: Int,
    val logo_path: String?=null,
    val name: String,
    val origin_country: String
)

