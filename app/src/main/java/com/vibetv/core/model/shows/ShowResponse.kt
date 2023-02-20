package com.vibetv.core.model.shows

import kotlinx.serialization.Serializable

@Serializable
data class ShowResponse(
    val results: List<ShowResults>
)
