package com.vibetv.designSystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import javax.annotation.concurrent.Immutable

@Immutable
data class BackgroundTheme(
    val color: Color = Color.Unspecified,
    val tonalVariation: Dp = Dp.Unspecified
)

val LocalBackgroundTheme = staticCompositionLocalOf { BackgroundTheme() }