package com.notebook.nioapp.domain.usecase

import com.notebook.nioapp.domain.repository.SettingsDataSource

class SelectStorageLocationUseCase(
    private val repository: SettingsDataSource
) {
    suspend operator fun invoke(path: String): Boolean {
        repository.saveRootPath(path)
        return repository.getRootPath()?.isNotEmpty() ?: false
    }
}