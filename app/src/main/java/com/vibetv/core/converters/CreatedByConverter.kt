package com.vibetv.core.converters

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.vibetv.core.model.shows.show_details.CreatedBy

class CreatedByConverter{
    @TypeConverter
    fun toCreatedByString(createdByConverter: List<CreatedBy>): String {
        return GsonBuilder().create().toJson(createdByConverter)
    }

    @TypeConverter
    fun toCreatedByList(data: String): List<CreatedBy>{
        val listType = object : TypeToken<List<CreatedBy>>() {}.type
        return GsonBuilder().create().fromJson(data, listType)
    }
}