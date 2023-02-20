package com.vibetv.core.converters

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class GenreIdConverter {
    @TypeConverter
    fun toGenreInt(genreResult: List<Int>): String {
        return GsonBuilder().create().toJson(genreResult)
    }

    @TypeConverter
    fun toGenreList(data: String): List<Int> {
        val listTYpe = object : TypeToken<List<Int>>() {}.type
        return GsonBuilder().create().fromJson(data, listTYpe)
    }
}