package com.vibetv.designSystem.components

import androidx.annotation.StringRes
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun MovieHeader(
    modifier: Modifier,
    @StringRes title: Int,
    @StringRes actionText: Int? = null,
    onMovieGridClicked: (() -> Unit)? = null,
    action: @Composable (() -> Unit)? = null,
) {
    ListItem(
        modifier = modifier,
        headlineText = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleMedium
            )
        },
        trailingContent = {
            TextButton(
                onClick = {
                    if (onMovieGridClicked != null) {
                        onMovieGridClicked()
                    }
                }

            ) {
                if (actionText != null)
                    Text(
                        text = stringResource(id = actionText),
                        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.tertiary),
                    )
            }
        }

    )

}