package com.example.mylntapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = DarkBlue,
    secondary = DarkBlueVariant,
    background = White,
    surface = White,
    onPrimary = White,
    onSecondary = White,
    onBackground = DarkBlue,
    onSurface = DarkBlue
)

private val LightColorScheme = lightColorScheme(
    primary = DarkBlue,
    secondary = DarkBlueVariant,
    background = White,
    surface = White,
    onPrimary = White,
    onSecondary = White,
    onBackground = DarkBlue,
    onSurface = DarkBlue
)

@Composable
fun MyLNTAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
} 