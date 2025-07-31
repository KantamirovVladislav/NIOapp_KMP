package com.notebook.nioapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.notebook.nioapp.di.desktopPlatformModule
import com.notebook.nioapp.domain.di.getSharedModules
import com.notebook.nioapp.root.RootComponentImpl
import com.notebook.nioapp.root.RootContent
import com.notebook.nioapp.utils.runOnUiThread
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun main() {
    val koinApp = initKoinDesktop()
    val lifecycle = LifecycleRegistry()

    val root =
        runOnUiThread {
            RootComponentImpl(
                componentContext = DefaultComponentContext(lifecycle = lifecycle),
            )
        }

    application {
        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)
        Window(
            onCloseRequest = ::exitApplication,
            title = "NIOapp",
            state = windowState
        ) {
            MaterialTheme {
                Surface {
                    RootContent(component = root, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }

}

fun initKoinDesktop(): KoinApplication {
    return startKoin {
        modules(desktopPlatformModule)
        modules(getSharedModules())
    }
}