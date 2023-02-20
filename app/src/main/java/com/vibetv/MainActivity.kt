package com.vibetv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vibetv.navigation.VibeApp
import com.vibetv.designSystem.theme.VibeTVTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VibeTVTheme {
                // A surface container using the 'background' color from the theme
                VibeApp()
            }
        }
    }
}

