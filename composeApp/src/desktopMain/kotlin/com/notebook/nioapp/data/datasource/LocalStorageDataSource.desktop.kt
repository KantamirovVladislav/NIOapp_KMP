package com.notebook.nioapp.data.datasource

import com.notebook.nioapp.domain.model.FileItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.FileDialog
import java.awt.Frame
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.Date
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

actual class LocalStorageDataSource actual constructor(private val settingsDataSource: SettingsDataSource) {

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

    // TODO add check empty directory for app settings and init existing files
    actual suspend fun containsAppFiles(path: String): Boolean = withContext(Dispatchers.IO) {
        true
    }

    actual suspend fun selectRootDirectory(): String? = withContext(Dispatchers.IO) {
        val fileChooser = JFileChooser(FileSystemView.getFileSystemView().homeDirectory).apply {
            dialogTitle = "Select Root Directory"
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            isAcceptAllFileFilterUsed = false
        }

        val result = fileChooser.showSaveDialog(null)

        if (result == JFileChooser.APPROVE_OPTION) {
            val selectedDir = fileChooser.selectedFile
            if (selectedDir != null && selectedDir.isDirectory) {
                val selectedPath = selectedDir.absolutePath
                selectedPath
            } else {
                null
            }
        } else {
            null
        }
    }

    actual suspend fun listFiles(directoryPath: String): List<FileItem> = withContext(Dispatchers.IO) {
        try {
            val path = Paths.get(directoryPath)
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

    actual suspend fun createFile(fullPath: String, data: ByteArray) = withContext(Dispatchers.IO) {

    }

    actual suspend fun readFile(fullPath: String): ByteArray? = withContext(Dispatchers.IO) {
        try {
            val path = Paths.get(fullPath)
            if (Files.exists(path) && !Files.isDirectory(path)) {
                Files.readAllBytes(path)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    actual fun isStorageWritable(path: String?): Boolean {
        return try {
            val testPath = Paths.get(path ?: settingsDataSource.getRootPath() ?: return false)
            Files.isWritable(testPath)
        } catch (e: Exception) {
            false
        }
    }
}