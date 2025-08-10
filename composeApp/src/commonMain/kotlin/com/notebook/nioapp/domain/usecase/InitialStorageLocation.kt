package com.notebook.nioapp.domain.usecase

import com.notebook.nioapp.domain.repository.SettingsDataSource

class InitialStorageLocation(
    private val repository: SettingsDataSource
) {
    suspend operator fun invoke(): String? {
        repository.getRootPath()
        return repository.getRootPath()
    }
}