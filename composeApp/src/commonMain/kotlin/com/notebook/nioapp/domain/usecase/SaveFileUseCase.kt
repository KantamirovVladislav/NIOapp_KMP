package com.notebook.nioapp.domain.usecase

import com.notebook.nioapp.domain.repository.StorageRepository

class SaveFileUseCase(
    private val repository: StorageRepository
) {
    suspend operator fun invoke(relativePath: String, content: ByteArray) {
        repository.createFile(relativePath, content)
    }
}