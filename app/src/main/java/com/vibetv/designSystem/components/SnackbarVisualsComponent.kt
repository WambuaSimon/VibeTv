package com.vibetv.designSystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

class SnackbarVisualsComponent(
    override val message:String,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
    val startIcon: (@Composable () -> Unit)? = null,
): SnackbarVisuals {
    override val withDismissAction = false
}


@Composable
internal fun Snackbar(
    snackbarData: SnackbarData,
){
    val actionLabel = snackbarData.visuals.actionLabel
    Row(
        modifier = Modifier
            .heightIn(min = 48.dp)
            .padding(vertical = 4.dp)
            .padding(start = 16.dp, end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Text(
            modifier = Modifier.weight(1f),
            text = snackbarData.visuals.message,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        if(actionLabel != null){
            TextButton(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                onClick = snackbarData::performAction ) {
                CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyLarge) {
                    Text(actionLabel)
                }
            }
        }
    }
}

