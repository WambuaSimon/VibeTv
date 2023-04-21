package com.vibetv.presentation.home.movie_grid

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MovieGridModel(
    title: String = ""
){
    var title:String by mutableStateOf(title)
}