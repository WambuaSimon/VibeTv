
package com.vibetv.presentation.movies

import com.vibetv.core.data.entities.GenreListEntity


data class GenreState(
    var genreList: List<GenreListEntity>? = emptyList()
)
