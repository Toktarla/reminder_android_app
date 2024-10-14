package com.example.reminder_android_app.ui.theme

import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color

// Define a CompositionLocal for theme management
val LocalTheme = staticCompositionLocalOf { false }

@Composable
fun ThemeProvider(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalTheme provides isDarkTheme) {
        MaterialTheme(
            colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme,
            content = content
        )
    }
}