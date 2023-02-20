package com.vibetv.core.converters

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.vibetv.core.model.season_details.Crew

class CrewConverter {

    @TypeConverter
    fun toCrewString(crewConverter: List<Crew>): String {
        return GsonBuilder().create().toJson(crewConverter)
    }

    @TypeConverter
    fun toCrewList(data: String): List<Crew>{
        val listType = object : TypeToken<List<Crew>>() {}.type
        return GsonBuilder().create().fromJson(data, listType)
    }
}