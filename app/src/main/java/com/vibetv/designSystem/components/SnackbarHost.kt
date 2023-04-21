package com.vibetv.designSystem.components

import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    snackbar: @Composable (SnackbarData)-> Unit = {Snackbar(it)}
) {
  SnackbarHost(
       hostState = hostState,
      modifier = modifier,
      snackbar = snackbar
    )
}