package com.notebook.nioapp.domain.di

import com.arkivanov.decompose.ComponentContext
import com.notebook.nioapp.domain.repository.DirectorySelectionComponent
import com.notebook.nioapp.domain.repository.OnBoardingComponent
import com.notebook.nioapp.domain.repository.UrlSelectionComponent
import com.notebook.nioapp.presentation.storageConfigurationScreen.DefaultDirectorySelectionComponent
import com.notebook.nioapp.presentation.onBoarding.DefaultOnBoardingComponent
import com.notebook.nioapp.presentation.urlSelectionScreen.DefaultUrlSelectionComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val directorySelectionModule = module {
    factory<DirectorySelectionComponent> { (componentContext: ComponentContext, onFinished: () -> Unit) ->
        DefaultDirectorySelectionComponent(
            componentContext = componentContext,
            selectStorageLocation = get(),
            initializeStorage = get(),
            localStorageDataSource = get(),
            mainContext = Dispatchers.Main,
            ioContext = Dispatchers.IO,
            onFinished = onFinished,
            directoryChooser = get()
        )
    }

    factory<OnBoardingComponent> { (componentContext: ComponentContext, onFinished: () -> Unit) ->
        DefaultOnBoardingComponent(
            componentContext = componentContext, onFinished = onFinished
        )
    }

    factory<UrlSelectionComponent> { (componentContext: ComponentContext, onFinished: () -> Unit) ->
        DefaultUrlSelectionComponent(
            componentContext = componentContext,
            onFinished = onFinished,
            checkUrlUseCase = get(),
            mainContext = Dispatchers.Main,
            ioContext = Dispatchers.IO,
        )
    }
}