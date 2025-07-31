package com.notebook.nioapp.domain.di

import com.notebook.nioapp.data.di.dataModule

fun getSharedModules() = listOf(
    dataModule,
    domainModule,
    directorySelectionModule
)