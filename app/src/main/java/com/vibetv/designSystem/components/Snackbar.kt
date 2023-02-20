package com.vibetv.designSystem.theme

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable

class Snackbar(
    override val message: String,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
    val startIcon: (@Composable () -> Unit)? = null,

    ) : SnackbarVisuals {
    override val withDismissAction = false

}
