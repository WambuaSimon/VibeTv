package com.vibetv.common

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

const val animationScale = 1

fun slideEnterTransition(): EnterTransition = slideInHorizontally { it }

fun slideExitTransition(): ExitTransition = slideOutHorizontally { -it }

fun slidePopEnterTransition(): EnterTransition = slideInHorizontally { -it }

fun slidePopExitTransition(): ExitTransition = slideOutHorizontally { it }

@ExperimentalAnimationApi
fun sharedZAxisEnterTransition(): EnterTransition {
    return fadeIn(
        animationSpec = tween(
            animationScale * 60,
            delayMillis = animationScale * 60,
            easing = LinearEasing,
        ),
    ) +
            scaleIn(
                initialScale = 0.8f,
                animationSpec = tween(durationMillis = animationScale * 300),
            )
}