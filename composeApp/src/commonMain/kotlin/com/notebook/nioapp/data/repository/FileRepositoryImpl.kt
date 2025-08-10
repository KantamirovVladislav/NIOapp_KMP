package com.notebook.nioapp.data.repository

import com.notebook.nioapp.domain.repository.DirectoryChooser
import com.notebook.nioapp.domain.repository.FileRepository
import com.notebook.nioapp.domain.repository.FileSystem
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.io.IOException

class FileRepositoryImpl(
    private val fileSystem: FileSystem,
    private val directoryChooser: DirectoryChooser,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FileRepository {

    override suspend fun saveDataToFile(data: ByteArray, fileName: String): Result<Unit> =
        withContext(dispatcher) {
            runCatching {
                if (!fileSystem.createFile(fileName, data)) {
                    throw IOException("Failed to create file") as Throwable
                }
            }
        }

    override suspend fun readDataFromFile(fileName: String): Result<ByteArray> {
        TODO("Not yet implemented")
    }

    override suspend fun selectDirectory(): Result<String> = withContext(dispatcher) {
        runCatching {
            directoryChooser.showDialog()?.let { path ->
                if (fileSystem.checkDirectoryAccess(path)) {
                    path
                } else {
                    throw IllegalStateException("No access to directory")
                }
            } ?: throw CancellationException("Directory selection cancelled")
        }
    }
}