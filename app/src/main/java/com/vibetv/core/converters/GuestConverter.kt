package com.vibetv.core.converters

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.vibetv.core.model.season_details.GuestStar

class GuestConverter {

    @TypeConverter
    fun toGuestString(crewConverter: List<GuestStar>): String {
        return GsonBuilder().create().toJson(crewConverter)
    }

    @TypeConverter
    fun toGuestStarList(data: String): List<GuestStar> {
        val listType = object : TypeToken<List<GuestStar>>() {}.type
        return GsonBuilder().create().fromJson(data, listType)
    }
}