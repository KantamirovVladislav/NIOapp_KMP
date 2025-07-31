package com.notebook.nioapp.presentation.common

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compose.NIOTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CirclesProgressIndicator(
    progress: Int = 0,
    maxCount: Int = 3,
    circleSize: Dp = 12.dp,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(maxCount) { index ->
            CircleProgressElement(isActive = index == progress, circleSize = circleSize)
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Composable
fun CircleProgressElement(
    isActive: Boolean = false,
    circleSize: Dp,
    modifier: Modifier = Modifier
) {
    NIOTheme {
        Box(
            modifier = modifier.clip(CircleShape)
                .background(
                    color = if (isActive)
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    else
                        MaterialTheme.colorScheme.surfaceVariant
                )
                .size(if (isActive) circleSize else circleSize / 1.5f)
                .shadow(
                    elevation = if (isActive) circleSize else 0.dp,
                    ambientColor = MaterialTheme.colorScheme.surfaceTint ,
                    spotColor = MaterialTheme.colorScheme.surfaceTint ,
                    shape = CircleShape
                )
                .animateContentSize(),
        )
    }
}

@Preview
@Composable
fun CirclesProgressIndicatorPreview() {
    NIOTheme {
        CirclesProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
}