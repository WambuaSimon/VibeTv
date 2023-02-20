package com.vibetv.core.converters

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.vibetv.core.model.shows.show_details.Network

class NetworksConverter {

    @TypeConverter
    fun toNetworkString(networkConverter: List<Network>): String {
        return GsonBuilder().create().toJson(networkConverter)
    }

    @TypeConverter
    fun toNetworkList(data: String): List<Network>{
        val listType = object : TypeToken<List<Network>>() {}.type
        return GsonBuilder().create().fromJson(data, listType)
    }
}