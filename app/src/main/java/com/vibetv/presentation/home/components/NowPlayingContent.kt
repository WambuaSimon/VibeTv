/*
package com.vibetv.presentation.home.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.vibetv.common.Constants.POSTER_PATH
import com.vibetv.domain.model.now_playing.NowPlayingResponse
import com.vibetv.presentation.home.HomeStateImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class)
@Composable
fun NowPlayingContent(
    nowPlayingResponse: NowPlayingResponse,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState()
    val itemCount = nowPlayingResponse.total_results

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(3000)
            tween<Float>(600)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    Column(
        modifier = modifier
    ) {

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalPager(count = itemCount, state = pagerState) { pagerIndex ->
            val item = nowPlayingResponse.results[pagerIndex]
            if (pagerIndex < nowPlayingResponse.total_results) {
                Card(
                    modifier = Modifier.width(350.dp)
                        .graphicsLayer {
                            val pageOffset = calculateCurrentOffsetForPage(pagerIndex).absoluteValue
                            lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }
                        }
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                        ).clickable {

                        }

                    ) {
                    AsyncImage(
                        modifier = modifier.height(height = 320.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(POSTER_PATH + item.poster_path)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth

                        )
                }
            }
        }


    }


}


*/
