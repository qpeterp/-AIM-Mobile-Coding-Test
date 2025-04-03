package com.qpeterp.assetmanagement.presentation.core.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Composable
fun AmButton(
    text: String = "",
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit = { Text(text) },
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed.value) 0.95f else 1f,
        animationSpec = tween(durationMillis = 50), label = "버튼 클릭 애니메이션"
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .then(Modifier.fillMaxWidth().padding(0.dp, 4.dp))
            .scale(scale),
        enabled = enabled,
        shape = RoundedCornerShape(0.dp),
        interactionSource = interactionSource,
    ) {
        content()
    }
}