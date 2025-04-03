package com.qpeterp.assetmanagement.presentation.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.filter { it.isDigit() }

        val formatted = buildString {
            for (i in trimmed.indices) {
                append(trimmed[i])
                when (i) {
                    2, 6 -> if (i != trimmed.lastIndex) append("-")
                }
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var extra = 0
                if (offset > 2) extra++
                if (offset > 6) extra++
                return (offset + extra).coerceAtMost(formatted.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                return offset - formatted.take(offset).count { it == '-' }
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}