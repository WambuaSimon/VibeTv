package com.vibetv.core.converters

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.vibetv.core.model.shared_models.ProductionCompany

class ProductionCompanyConverter{
    @TypeConverter
    fun toProductionCompanyString(productionCompanyResult: List<ProductionCompany>): String {
        return GsonBuilder().create().toJson(productionCompanyResult)
    }

    @TypeConverter
    fun toProductionCompanyList(data: String): List<ProductionCompany> {
        val listTYpe = object : TypeToken<List<ProductionCompany>>() {}.type
        return GsonBuilder().create().fromJson(data, listTYpe)
    }
}