package com.notebook.nioapp.domain.usecase

import com.notebook.nioapp.domain.repository.StorageRepository

class InitializeStorageUseCase(
    private val repository: StorageRepository
) {
    suspend operator fun invoke(): Boolean {
        val info = repository.getStorageInfo()
        println(info)
        return info != null && info.isWritable
    }
}