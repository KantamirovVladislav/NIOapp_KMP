package com.notebook.nioapp.domain.repository

import com.notebook.nioapp.domain.model.FileItem
import com.notebook.nioapp.domain.model.StorageInfo

interface StorageRepository {
    suspend fun selectRootDirectory(): String?
    suspend fun getStorageInfo(): StorageInfo?
    suspend fun saveStoragePath(path: String)
    suspend fun listFiles(relativePath: String): List<FileItem>
    suspend fun createFile(relativePath: String, content: ByteArray)
    suspend fun readFile(relativePath: String): ByteArray?
    suspend fun deleteFile(relativePath: String)
}