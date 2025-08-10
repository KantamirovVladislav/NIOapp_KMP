package com.notebook.nioapp.domain.repository

import com.arkivanov.decompose.value.Value

interface DirectorySelectionComponent {
    val state: Value<StorageState>

    val rootPath: Value<String>

    val checkPathResult: Value<Boolean>

    fun openDirectoryDialog()
    fun confirmDirectorySelection()

    sealed class StorageState {
        object Loading : StorageState()
        object NeedConfiguration : StorageState()
        object Configured : StorageState()
        data class Error(val message: String) : StorageState()
    }
}