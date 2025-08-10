package com.notebook.nioapp.data.di

import com.notebook.nioapp.data.datasource.SettingsDataSourceImpl
import com.notebook.nioapp.domain.repository.SettingsDataSource
import com.russhwolf.settings.Settings
import org.koin.dsl.module

val settingsModule = module {
    single<Settings> {
        Settings()
    }
    single<SettingsDataSource> {
        SettingsDataSourceImpl(get())
    }
}