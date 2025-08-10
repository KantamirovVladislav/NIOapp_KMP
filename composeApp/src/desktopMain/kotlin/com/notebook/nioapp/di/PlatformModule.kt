package com.notebook.nioapp.di

import com.notebook.nioapp.data.repository.FileRepositoryImpl
import com.notebook.nioapp.domain.repository.DirectoryChooser
import com.notebook.nioapp.domain.repository.FileRepository
import com.notebook.nioapp.domain.repository.FileSystem
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.PropertiesSettings
import com.russhwolf.settings.Settings
import org.koin.dsl.module
import java.io.File
import java.util.Properties

val desktopPlatformModule = module {
    single<FileSystem> { FileSystem() }
    single<DirectoryChooser> { DirectoryChooser() }
    single<FileRepository> { FileRepositoryImpl(get(), get()) }
}