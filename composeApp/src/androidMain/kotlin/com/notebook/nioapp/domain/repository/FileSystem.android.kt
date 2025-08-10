package com.notebook.nioapp.domain.repository

import com.notebook.nioapp.domain.model.FileItem

actual class FileSystem {
    actual suspend fun createFile(path: String, content: ByteArray): Boolean {
        TODO("Not yet implemented")
    }

    actual suspend fun readFile(path: String): ByteArray? {
        TODO("Not yet implemented")
    }

    actual suspend fun listFiles(path: String): List<FileItem> {
        TODO("Not yet implemented")
    }

    actual suspend fun isStorageWritable(path: String?): Boolean {
        TODO("Not yet implemented")
    }

    actual suspend fun isStorageReadable(): Boolean {
        TODO("Not yet implemented")
    }

    actual suspend fun isDirectoryEmpty(path: String): Boolean {
        TODO("Not yet implemented")
    }

    actual suspend fun checkDirectoryAccess(path: String): Boolean {
        TODO("Not yet implemented")
    }

    actual suspend fun containsAppFiles(
        path: String,
        extensions: List<String>
    ): Boolean {
        TODO("Not yet implemented")
    }
}