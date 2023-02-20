package com.vibetv.core.api

import kotlinx.serialization.Serializable

@Serializable
data class VibeTVErrorDetail(
    val status_code: Int,
    val status_message: String? = null
)
