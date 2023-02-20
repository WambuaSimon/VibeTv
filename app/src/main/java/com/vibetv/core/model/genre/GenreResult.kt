package com.vibetv.core.model.genre

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreResult(
  @SerialName("genres")
  val genreList: List<GenreList>
)
