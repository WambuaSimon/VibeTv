package com.vibetv.presentation.movie_details.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RowWithIcon(
    icon: @Composable () -> Unit,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        title()
        icon()
    }
}


@Composable
@Preview
fun RowWithIconPreview() {
    RowWithIcon(
        icon = { Icons.Rounded.Settings },
        title = { Text(text = "Hello") },
        modifier = Modifier
    )
}