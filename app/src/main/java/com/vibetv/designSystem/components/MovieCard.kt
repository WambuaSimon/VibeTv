package com.vibetv.designSystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vibetv.common.shimmerBrush
import com.vibetv.designSystem.theme.VibeIcons

@Composable
fun MovieCard(
    modifier: Modifier,
    posterPathBaseUrl: String,
    poster: String,
    voteAverage: Double,
    onClick: (Int) -> Unit,
    id: Int,
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Card(modifier = Modifier.clickable { onClick(id) }) {
            AsyncImage(
                modifier = modifier
                    .background(
                        shimmerBrush(
                            targetValue = 1300f,
                            showShimmer = true
                        )
                    )
                    .align(Alignment.CenterHorizontally)
                    .height(170.dp)
                    .width(100.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(posterPathBaseUrl + poster)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,

                )
        }
        Row {
            VibeIcon(
                imageVector = VibeIcons.Star,
                modifier = modifier,
                contentDescription = null,
                size = 15.dp
            )
            Spacer(modifier = modifier.width(4.dp))
            Text(
                text = voteAverage.toString(),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}


