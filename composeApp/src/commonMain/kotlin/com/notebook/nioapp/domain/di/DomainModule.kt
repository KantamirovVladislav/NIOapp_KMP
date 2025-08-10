package com.notebook.nioapp.domain.di

import com.notebook.nioapp.domain.usecase.CheckUrlUseCase
import com.notebook.nioapp.domain.usecase.InitialStorageLocation
import com.notebook.nioapp.domain.usecase.SelectStorageLocationUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { SelectStorageLocationUseCase(get()) }
    factory { CheckUrlUseCase(get(), get()) }
    factory { InitialStorageLocation(get()) }
}