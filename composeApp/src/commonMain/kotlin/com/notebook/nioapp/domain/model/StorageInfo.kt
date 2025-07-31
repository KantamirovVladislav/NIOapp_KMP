package com.notebook.nioapp.domain.model

data class StorageInfo(
    val rootPath: String,
    val isWritable: Boolean,
    val lastAccessed: Long
)