package com.notebook.nioapp.data.datasource

import com.notebook.nioapp.domain.model.FileItem

expect class LocalStorageDataSource(
    settingsDataSource: SettingsDataSource
) {
    suspend fun isDirectoryEmpty(path: String): Boolean
    suspend fun checkDirectoryAccess(path: String): Boolean
    suspend fun containsAppFiles(path: String): Boolean
    suspend fun selectRootDirectory(): String?
    suspend fun listFiles(directoryPath: String): List<FileItem>
    suspend fun createFile(fullPath: String, data: ByteArray)
    suspend fun readFile(fullPath: String): ByteArray?
    fun isStorageWritable(path: String?): Boolean
}