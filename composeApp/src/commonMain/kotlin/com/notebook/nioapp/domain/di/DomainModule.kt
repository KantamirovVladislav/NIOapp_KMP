package com.notebook.nioapp.domain.di

import com.notebook.nioapp.domain.usecase.InitializeStorageUseCase
import com.notebook.nioapp.domain.usecase.SaveFileUseCase
import com.notebook.nioapp.domain.usecase.SelectStorageLocationUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { InitializeStorageUseCase(get()) }
    factory { SelectStorageLocationUseCase(get()) }
    factory { SaveFileUseCase(get()) }
}