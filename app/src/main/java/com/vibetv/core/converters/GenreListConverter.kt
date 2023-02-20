package com.vibetv.core.converters

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.vibetv.core.model.shared_models.Genre

class GenreListConverter
 {
    @TypeConverter
    fun toGenreString(genreResult: List<Genre>): String {
        return GsonBuilder().create().toJson(genreResult)
    }

    @TypeConverter
    fun toGenreList(data: String): List<Genre> {
        val listTYpe = object : TypeToken<List<Genre>>() {}.type
        return GsonBuilder().create().fromJson(data, listTYpe)
    }
}