package com.notebook.nioapp.domain.repository

interface FileRepository {
    suspend fun saveDataToFile(data: ByteArray, fileName: String): Result<Unit>
    suspend fun readDataFromFile(fileName: String): Result<ByteArray>
    suspend fun selectDirectory(): Result<String>
}