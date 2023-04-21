package com.vibetv.designSystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vibetv.common.Constants
import com.vibetv.common.shimmerBrush

@Composable
fun AsyncImagePoster(
    modifier: Modifier,
    posterImage: String?
) {
    Card(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = modifier
                .background(
                    shimmerBrush(
                        targetValue = 1300f,
                        showShimmer = true
                    )
                )
                .height(150.dp)
                .width(100.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(Constants.POSTER_PATH + posterImage)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,

            )
    }
}