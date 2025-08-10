package com.notebook.nioapp.domain.repository

import com.notebook.nioapp.domain.model.FileItem

expect class FileSystem {
    suspend fun createFile(path: String, content: ByteArray = byteArrayOf()): Boolean
    suspend fun readFile(path: String): ByteArray?
    suspend fun listFiles(path: String): List<FileItem>
    suspend fun isStorageWritable(path: String?): Boolean
    suspend fun isStorageReadable(): Boolean
    suspend fun isDirectoryEmpty(path: String): Boolean
    suspend fun checkDirectoryAccess(path: String): Boolean
    suspend fun containsAppFiles(path: String, extensions: List<String>): Boolean
}