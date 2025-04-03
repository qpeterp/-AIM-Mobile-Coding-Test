package com.qpeterp.assetmanagement.presentation.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun ErrorText(
    text: String,
    isVisible: Boolean,
    fontSize: TextUnit = 12.sp,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = 1,
    fontWeight: FontWeight? = null,
    modifier: Modifier = Modifier,
) {
    if (isVisible) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.error,
            fontWeight = fontWeight,
            fontSize = fontSize,
            textAlign = textAlign,
            maxLines = maxLines,
            modifier = modifier.then(Modifier.fillMaxWidth())
        )
    }
}