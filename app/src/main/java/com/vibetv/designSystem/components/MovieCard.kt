package com.vibetv.designSystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vibetv.common.Constants
import com.vibetv.designSystem.theme.VibeIcons
import com.vibetv.designSystem.theme.VibeTVTheme

@Composable
fun MovieCard(
    modifier: Modifier,
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
        AsyncImagePoster(modifier = modifier.clickable { onClick(id) }, posterImage = poster)

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

@Preview
@Composable
fun MovieCardPreview() {
    VibeTVTheme {
        MovieCard(
            modifier = Modifier,
            poster = "${Constants.POSTER_PATH}/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
            voteAverage = 7.5,
            onClick = {},
            id = 323
        )
    }
}


