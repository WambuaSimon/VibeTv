package com.vibetv.designSystem.theme

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import com.vibetv.common.utils.Resource
import com.vibetv.core.network.NoNetworkException
import com.vibetv.designSystem.components.SnackbarVisualsComponent

@OptIn(ExperimentalMaterial3Api::class)
suspend fun ShowErrorSnackbar(
    context: Context,
    snackbarHostState: SnackbarHostState,
    error: Resource.Error<Unit>?,
    @StringRes fallbackMessage: Int,
    @StringRes retryButtonLabel: Int? = null,
    retry: (() -> Unit)? = null,

) {
    val result = snackbarHostState.showSnackbar(
        SnackbarVisualsComponent(
            message =
            error?.errorMessage(context)
                ?: context.getString(fallbackMessage),
            actionLabel =
            when (retryButtonLabel) {
                null -> null
                else -> when (error?.throwable) {
                    is NoNetworkException -> null
                    else -> context.getString(retryButtonLabel)
                }
            },
            duration = when {
                error?.throwable is NoNetworkException || retryButtonLabel != null ->
                    SnackbarDuration.Indefinite

                else -> SnackbarDuration.Short
            },

            )
    )
    if (result == SnackbarResult.ActionPerformed) {
        retry?.invoke()
    }
}