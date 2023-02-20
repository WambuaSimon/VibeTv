package com.vibetv.core.converters

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.vibetv.core.model.shows.show_details.SpokenLanguage

class SpokenLanguageConverter {
    fun toSpokenLanguageString(spokenLanguage: List<SpokenLanguage>): String {
        return GsonBuilder().create().toJson(spokenLanguage)
    }

    @TypeConverter
    fun toSpokenLanguageList(data: String): List<SpokenLanguage> {
        val listTYpe = object : TypeToken<List<SpokenLanguage>>() {}.type
        return GsonBuilder().create().fromJson(data, listTYpe)
    }
}