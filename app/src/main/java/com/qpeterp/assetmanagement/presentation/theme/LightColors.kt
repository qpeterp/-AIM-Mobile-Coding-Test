package com.qpeterp.assetmanagement.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF48C9CF),
    onPrimary = Color.White,
    secondary = Color(0xFFFF6B6B),
    onSecondary = Color.White,
    background = Color(0xFFF0F9F9),
    surface = Color(0xFFE3F2F2),
    onSurface = Color(0xFF2A313A),
    error = Color(0xFFD32F2F),
    onError = Color.White,
)

@Composable
fun AssetManagementTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}