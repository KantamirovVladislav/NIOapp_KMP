package com.notebook.nioapp.domain.model

data class FileItem(
    val path: String,
    val name: String,
    val size: Long,
    val lastModified: Long,
    val isDirectory: Boolean
)