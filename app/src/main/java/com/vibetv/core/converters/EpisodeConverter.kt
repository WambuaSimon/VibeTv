package com.vibetv.core.converters

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.vibetv.core.model.season_details.Episode

class EpisodeConverter {

    @TypeConverter
    fun toEpisodeString(crewConverter: List<Episode>): String {
        return GsonBuilder().create().toJson(crewConverter)
    }

    @TypeConverter
    fun toEpisodeList(data: String): List<Episode>{
        val listType = object : TypeToken<List<Episode>>() {}.type
        return GsonBuilder().create().fromJson(data, listType)
    }
}