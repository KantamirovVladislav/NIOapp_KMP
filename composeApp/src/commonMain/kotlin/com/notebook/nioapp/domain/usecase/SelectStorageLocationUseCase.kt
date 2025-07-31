package com.notebook.nioapp.domain.usecase

import com.notebook.nioapp.domain.repository.StorageRepository

class SelectStorageLocationUseCase(
    private val repository: StorageRepository
) {
    suspend operator fun invoke(path: String): Boolean {
        repository.saveStoragePath(path)
        return repository.getStorageInfo()?.isWritable ?: false
    }
}