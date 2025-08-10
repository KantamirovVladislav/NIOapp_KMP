package com.notebook.nioapp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import nioapp.composeapp.generated.resources.Res
import nioapp.composeapp.generated.resources.cancel_svg
import nioapp.composeapp.generated.resources.confirm_svg
import nioapp.composeapp.generated.resources.folder_open_svg
import org.jetbrains.compose.resources.painterResource

@Composable
fun UrlCheckPresenter(
    url: String?,
    onUrlChange: (String) -> Unit,
    checkResult: Boolean,
    modifier: Modifier = Modifier
) {
    var painter: Painter = painterResource(Res.drawable.cancel_svg)
    if (checkResult) {
        painter = painterResource(Res.drawable.confirm_svg)
    }
    OutlinedTextField(
        value = url ?: "",
        onValueChange = onUrlChange,
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        isError = !checkResult,
        maxLines = 1,
        trailingIcon = {
            Icon(
                painter,
                null,
                modifier = Modifier.size(32.dp).clip(CircleShape).padding(4.dp),
                tint = if (checkResult) Color.Green else Color.Red
            )
        },
        placeholder = {
            Text(text = "https://nioapp.com")
        },
        shape = RoundedCornerShape(corner = CornerSize(12.dp))
    )
}