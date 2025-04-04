package com.qpeterp.assetmanagement.presentation.utils

import androidx.compose.ui.graphics.Color

fun darkenColor(color: Color, factor: Float): Color {
    return Color(
        red = (color.red * factor).coerceIn(0f, 4f),
        green = (color.green * factor).coerceIn(0f, 4f),
        blue = (color.blue * factor).coerceIn(0f, 4f),
        alpha = color.alpha
    )
}