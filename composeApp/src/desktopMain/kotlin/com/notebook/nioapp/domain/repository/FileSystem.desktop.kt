package com.notebook.nioapp.domain.repository

import com.notebook.nioapp.domain.model.FileItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.use

actual class FileSystem {
    actual suspend fun createFile(path: String, content: ByteArray): Boolean {
        TODO("Not yet implemented")
    }

    actual suspend fun readFile(path: String): ByteArray? {
        TODO("Not yet implemented")
    }

    actual suspend fun isDirectoryEmpty(path: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val dirPath = Paths.get(path)
            if (!Files.isDirectory(dirPath)) return@withContext false

            Files.list(dirPath).use { stream ->
                !stream.findAny().isPresent
            }
        } catch (e: Exception) {
            false
        }
    }

    actual suspend fun checkDirectoryAccess(path: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val dirPath = Paths.get(path)
            Files.isReadable(dirPath) && Files.isWritable(dirPath)
        } catch (e: Exception) {
            false
        }
    }

    actual suspend fun containsAppFiles(
        path: String,
        extensions: List<String>
    ): Boolean = withContext(Dispatchers.IO) {
        true
    }

    actual suspend fun listFiles(path: String): List<FileItem> = withContext(Dispatchers.IO) {
        try {
            val path = Paths.get(path)
            if (!Files.exists(path) || !Files.isDirectory(path)) {
                return@withContext emptyList()
            }

            Files.list(path)
                .map { filePath ->
                    val attributes = Files.readAttributes(filePath, "*")
                    FileItem(
                        name = filePath.fileName.toString(),
                        path = filePath.toString(),
                        isDirectory = Files.isDirectory(filePath),
                        size = if (Files.isDirectory(filePath)) 0 else Files.size(filePath),
                        lastModified = Files.getLastModifiedTime(filePath).toMillis()
                    )
                }
                .toList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    actual suspend fun isStorageWritable(path: String?): Boolean {
        return try {
            val testPath = Paths.get(path ?: return false)
            Files.isWritable(testPath)
        } catch (e: Exception) {
            false
        }
    }

    actual suspend fun isStorageReadable(): Boolean {
        TODO("Not yet implemented")
    }
}