package com.notebook.nioapp.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.notebook.nioapp.presentation.onBoarding.OnBoardingScreen


@Preview(
    name = "Phone Light",
    group = "Phone",
    device = Devices.PIXEL_4,
    showBackground = true
)
@Preview(
    name = "Phone Dark",
    group = "Phone",
    device = Devices.PIXEL_4,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Preview(
    name = "Desktop FullHD Light",
    group = "Desktop",
    device = Devices.DESKTOP,
    showBackground = true
)
@Preview(
    name = "Desktop FullHD Dark",
    group = "Desktop",
    device = Devices.DESKTOP,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun OnBoardingScreenPreviews() {
    //OnBoardingScreen()
}