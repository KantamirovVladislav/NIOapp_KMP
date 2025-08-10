package com.notebook.nioapp.domain.di

import com.notebook.nioapp.data.di.dataModule
import com.notebook.nioapp.data.di.settingsModule

fun getSharedModules() = listOf(
    settingsModule,
    networkModule,
    dataModule,
    domainModule,
    directorySelectionModule
)