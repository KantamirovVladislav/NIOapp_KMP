package com.notebook.nioapp.data.di

import com.notebook.nioapp.data.datasource.LocalStorageDataSource
import com.notebook.nioapp.data.datasource.SettingsDataSource
import com.notebook.nioapp.data.datasource.SettingsDataSourceImpl
import com.notebook.nioapp.data.repository.StorageRepositoryImpl
import com.notebook.nioapp.domain.repository.StorageRepository
import com.russhwolf.settings.Settings
import org.koin.dsl.module

val dataModule = module {
    single<Settings> {
        Settings()
    }
    single<SettingsDataSource> {
        SettingsDataSourceImpl(get())
    }
    single<LocalStorageDataSource> {
        LocalStorageDataSource(get())
    }
    single<StorageRepository> { StorageRepositoryImpl(get(), get()) }
}