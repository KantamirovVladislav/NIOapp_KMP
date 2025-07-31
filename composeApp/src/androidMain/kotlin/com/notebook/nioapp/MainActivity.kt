package com.notebook.nioapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import com.notebook.nioapp.domain.di.getSharedModules
import com.notebook.nioapp.presentation.di.androidPresentationModule
import com.notebook.nioapp.root.RootComponentImpl
import com.notebook.nioapp.root.RootContent
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        initKoinAndroid()

        val root =
            RootComponentImpl(
                componentContext = defaultComponentContext(),
            )

        setContent {
            MaterialTheme {
                Surface {
                    RootContent(component = root, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

fun initKoinAndroid(): KoinApplication {
    return startKoin {
        modules(androidPresentationModule)
        modules(getSharedModules())
    }
}
