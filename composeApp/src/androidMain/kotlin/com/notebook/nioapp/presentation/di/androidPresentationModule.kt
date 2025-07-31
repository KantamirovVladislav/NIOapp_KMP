package com.notebook.nioapp.presentation.di

import com.notebook.nioapp.presentation.viewmodel.AndroidStorageViewModel
import org.koin.dsl.module

val androidPresentationModule = module {
    factory {
        AndroidStorageViewModel(get())
    }
    single { StorageViewModel(get(), get()) }
}