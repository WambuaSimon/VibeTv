package com.vibetv.core.converters

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class ListStringConverter {
    @TypeConverter
    fun toListString(genreResult: List<String>): String {
        return GsonBuilder().create().toJson(genreResult)
    }

    @TypeConverter
    fun toListList(data: String): List<String> {
        val listTYpe = object : TypeToken<List<String>>() {}.type
        return GsonBuilder().create().fromJson(data, listTYpe)
    }
}