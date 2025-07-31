package com.notebook.nioapp.domain.di

import com.arkivanov.decompose.ComponentContext
import com.notebook.nioapp.domain.repository.DirectorySelectionComponent
import com.notebook.nioapp.domain.repository.OnBoardingComponent
import com.notebook.nioapp.presentation.viewmodel.DefaultDirectorySelectionComponent
import com.notebook.nioapp.presentation.viewmodel.DefaultOnBoardingComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val directorySelectionModule = module {
    factory<DirectorySelectionComponent> { (componentContext: ComponentContext, onFinished: () -> Unit) ->
        DefaultDirectorySelectionComponent(
            componentContext = componentContext,
            initializeStorage = get(),
            selectStorageLocation = get(),
            localStorageDataSource = get(),
            mainContext = Dispatchers.Main,
            ioContext = Dispatchers.IO,
            onFinished = onFinished
        )
    }
    factory<OnBoardingComponent> { (componentContext: ComponentContext, onFinished: () -> Unit) ->
        DefaultOnBoardingComponent(
            componentContext = componentContext, onFinished = onFinished
        )
    }
}