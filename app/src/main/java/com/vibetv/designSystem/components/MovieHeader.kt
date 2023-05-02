package com.vibetv.designSystem.components

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieHeader(
    modifier: Modifier,
    @StringRes title: Int,
    @StringRes actionText: Int? = null,
    onMovieGridClicked: (String,String?) -> Unit,
    action: @Composable (() -> Unit)? = null,
) {
    val context = LocalContext.current
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
                onClick = { onMovieGridClicked(context.getString(title),"") }

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