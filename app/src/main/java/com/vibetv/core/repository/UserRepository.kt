package com.vibetv.core.repository

import com.vibetv.core.model.user_data.DarkThemeConfig
import com.vibetv.core.model.user_data.ThemeBrand
import com.vibetv.core.model.user_data.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val userData: Flow<UserData>

    suspend fun setThemeBrand(themeBrand: ThemeBrand)
    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)
    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)
}