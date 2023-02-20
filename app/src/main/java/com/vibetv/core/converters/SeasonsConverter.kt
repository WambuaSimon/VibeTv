package com.vibetv.core.converters

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.vibetv.core.model.shows.show_details.Season

class SeasonsConverter {
    @TypeConverter
    fun toNetworkString(seasonConverter: List<Season>): String {
        return GsonBuilder().create().toJson(seasonConverter)
    }

    @TypeConverter
    fun toSeasonList(data: String): List<Season>{
        val listType = object : TypeToken<List<Season>>() {}.type
        return GsonBuilder().create().fromJson(data, listType)
    }
}