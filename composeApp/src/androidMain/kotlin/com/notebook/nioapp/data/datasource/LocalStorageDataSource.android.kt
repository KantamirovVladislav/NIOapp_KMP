package com.notebook.nioapp.data.datasource

import com.notebook.nioapp.domain.model.FileItem

actual class LocalStorageDataSource actual constructor(settingsDataSource: SettingsDataSource) {
    actual suspend fun isDirectoryEmpty(path: String): Boolean {
        TODO("Not yet implemented")
    }

    actual suspend fun checkDirectoryAccess(path: String): Boolean {
        TODO("Not yet implemented")
    }

    actual suspend fun containsAppFiles(path: String): Boolean {
        TODO("Not yet implemented")
    }

    actual suspend fun selectRootDirectory(): String? {
        TODO("Not yet implemented")
    }

    actual suspend fun listFiles(directoryPath: String): List<FileItem> {
        TODO("Not yet implemented")
    }

    actual suspend fun createFile(fullPath: String, data: ByteArray) {
    }

    actual suspend fun readFile(fullPath: String): ByteArray? {
        TODO("Not yet implemented")
    }

    actual fun isStorageWritable(path: String?): Boolean {
        TODO("Not yet implemented")
    }
}