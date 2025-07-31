package com.notebook.nioapp.domain.di

import com.arkivanov.decompose.ComponentContext
import com.notebook.nioapp.domain.repository.DirectorySelectionComponent
import com.notebook.nioapp.presentation.viewmodel.DefaultDirectorySelectionComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val directorySelectionModule = module {
    factory<DirectorySelectionComponent> { (componentContext: ComponentContext, onFinished: (Boolean) -> Unit) ->
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
}