package com.notebook.nioapp.data.repository

import com.notebook.nioapp.data.datasource.LocalStorageDataSource
import com.notebook.nioapp.data.datasource.SettingsDataSource
import com.notebook.nioapp.domain.model.FileItem
import com.notebook.nioapp.domain.model.StorageInfo
import com.notebook.nioapp.domain.repository.StorageRepository
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class StorageRepositoryImpl(
    private val localStorageDataSource: LocalStorageDataSource,
    private val settingsDataSource: SettingsDataSource
) : StorageRepository {

    override suspend fun selectRootDirectory(): String? {
        val path = settingsDataSource.getRootPath()
        return path
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun getStorageInfo(): StorageInfo? {
        val path = settingsDataSource.getRootPath()
        return path?.let {
            StorageInfo(
                rootPath = it,
                isWritable = localStorageDataSource.isStorageWritable(it),
                lastAccessed = Clock.System.now().toEpochMilliseconds()
            )
        }
    }

    override suspend fun saveStoragePath(path: String) {
        settingsDataSource.saveRootPath(path)
    }

    override suspend fun listFiles(relativePath: String): List<FileItem> {
        val root = settingsDataSource.getRootPath() ?: return emptyList()
        return localStorageDataSource.listFiles("$root/$relativePath")
    }

    override suspend fun createFile(relativePath: String, content: ByteArray) {
        val root =  settingsDataSource.getRootPath() ?: throw IllegalStateException("Storage not configured")
        localStorageDataSource.createFile("$root/$relativePath", content)
    }

    override suspend fun readFile(relativePath: String): ByteArray? {
        val root =  settingsDataSource.getRootPath() ?: return null
        return localStorageDataSource.readFile("$root/$relativePath")
    }

    //TODO Create delete logic
    override suspend fun deleteFile(relativePath: String) {
        val root =  settingsDataSource.getRootPath() ?: throw IllegalStateException("Storage not configured")
    }
}