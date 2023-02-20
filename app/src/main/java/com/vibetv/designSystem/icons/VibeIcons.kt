package com.vibetv.designSystem.theme

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.Timelapse
import androidx.compose.ui.graphics.vector.ImageVector
import com.vibetv.R


object VibeIcons {
    const val Home = R.drawable.home
    const val Movies = R.drawable.movie
    const val TvShow = R.drawable.tv
    const val Heart = R.drawable.favourite
    val Rating = Icons.Rounded.StarHalf
    val Duration = Icons.Rounded.Timelapse
    val Favourites = Icons.Rounded.Favorite
    val Link = Icons.Rounded.Link
    var Star = Icons.Rounded.StarHalf

}

sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}