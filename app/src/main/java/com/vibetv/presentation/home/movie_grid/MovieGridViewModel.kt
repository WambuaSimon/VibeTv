package com.vibetv.presentation.home.movie_grid

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieGridViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val title: String = savedStateHandle["title"]!!

    val model: MovieGridModel = MovieGridModel(
        title = title
    )
    init {
        Log.d("Stuff", "Title: " + title)
    }
}