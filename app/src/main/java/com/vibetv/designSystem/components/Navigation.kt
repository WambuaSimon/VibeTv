package com.vibetv.designSystem.theme

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.VibeNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    ){
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = selectedIcon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = VibeNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = VibeNavigationDefaults.navigationContentColor(),
            selectedTextColor = VibeNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = VibeNavigationDefaults.navigationContentColor(),
            indicatorColor = VibeNavigationDefaults.navigationIndicatorColor()
        )
    )
}
@Composable
fun VibeNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    NavigationBar(
        modifier = modifier,
        contentColor = VibeNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content
    )
}

object VibeNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant
    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer
    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}

