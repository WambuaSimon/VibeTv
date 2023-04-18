package com.vibetv.designSystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vibetv.common.Constants
import com.vibetv.common.shimmerBrush

@Composable
fun AsyncImageBackdrop(
    modifier:Modifier,
    backdropImage:String?
){
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            modifier = modifier .background(
                shimmerBrush(
                    targetValue = 1300f,
                    showShimmer = true
                )
            ),
            model = ImageRequest.Builder(LocalContext.current)
                .data(Constants.POSTER_PATH + backdropImage)
                .crossfade(true)
                .crossfade(durationMillis = 250)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,

            )
    }
}