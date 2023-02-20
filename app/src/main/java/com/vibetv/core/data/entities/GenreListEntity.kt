package com.vibetv.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vibetv.core.model.genre.GenreList

@Entity(tableName = "genre")
data class GenreListEntity(
    @PrimaryKey
    val id: Int?,
    val name: String?
) {
    internal companion object {
        fun GenreList.toGenreEntity() = GenreListEntity(
            id = id,
            name = name
        )
    }

}
