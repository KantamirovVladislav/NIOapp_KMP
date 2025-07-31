package com.notebook.nioapp.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.notebook.nioapp.presentation.onBoarding.OnBoardingScreen
import com.notebook.nioapp.presentation.viewmodel.StorageConfigurationScreen

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.DirectorySelectionChild -> StorageConfigurationScreen(component = child.component)
            is RootComponent.Child.OnBoarding -> OnBoardingScreen(component = child.component)
        }
    }
}