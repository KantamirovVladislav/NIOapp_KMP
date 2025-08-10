package com.notebook.nioapp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import coil3.Image
import coil3.compose.AsyncImage
import com.example.compose.NIOTheme
import nioapp.composeapp.generated.resources.Res
import nioapp.composeapp.generated.resources.cancel_svg
import nioapp.composeapp.generated.resources.compose_multiplatform
import nioapp.composeapp.generated.resources.confirm_svg
import nioapp.composeapp.generated.resources.folder_open_svg
import org.jetbrains.compose.resources.painterResource

@Composable
fun FilePathPresenter(
    filePath: String?,
    checkResult: Boolean,
    openFolderClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clip(RoundedCornerShape(corner = CornerSize(12.dp)))
            .background(MaterialTheme.colorScheme.surfaceContainer).padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = filePath ?: "", modifier = Modifier.weight(0.8f).fillMaxWidth())

        var painter: Painter = painterResource(Res.drawable.cancel_svg)

        if (checkResult) {
            painter = painterResource(Res.drawable.confirm_svg)
        }

        Icon(
            painter,
            null,
            modifier = Modifier.size(32.dp).clip(CircleShape).padding(4.dp),
            tint = if (checkResult) Color.Green else Color.Red
        )

        Spacer(modifier = Modifier.width(6.dp))

        Icon(
            painter = painterResource(Res.drawable.folder_open_svg),
            null,
            modifier = Modifier.size(32.dp).clip(CircleShape).clickable {
                openFolderClick()
            }.pointerHoverIcon(PointerIcon.Hand).padding(4.dp)
        )
    }
}