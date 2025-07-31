package com.notebook.nioapp.presentation.viewmodel

import androidx.lifecycle.coroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.notebook.nioapp.data.datasource.LocalStorageDataSource
import com.notebook.nioapp.domain.repository.DirectorySelectionComponent
import com.notebook.nioapp.domain.usecase.InitializeStorageUseCase
import com.notebook.nioapp.domain.usecase.SelectStorageLocationUseCase
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultDirectorySelectionComponent(
    componentContext: ComponentContext,
    private val initializeStorage: InitializeStorageUseCase,
    private val selectStorageLocation: SelectStorageLocationUseCase,
    private val localStorageDataSource: LocalStorageDataSource,
    private val onFinished: () -> Unit,
    mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
) : DirectorySelectionComponent, ComponentContext by componentContext {
    private val _state =
        MutableValue<DirectorySelectionComponent.StorageState>(DirectorySelectionComponent.StorageState.Loading)
    override val state: Value<DirectorySelectionComponent.StorageState> = _state

    private val scope = coroutineScope(mainContext + SupervisorJob())

    private val _showDirectoryDialog = MutableValue(false)
    override val showDirectoryDialog: Value<Boolean> = _showDirectoryDialog

    init {
        checkStorageConfiguration()
    }

    private fun checkStorageConfiguration() {
        scope.launch {
            withContext(ioContext)
            {
                val isConfigured = initializeStorage()
                _state.update {
                    if (isConfigured) {
                        onFinished()
                        DirectorySelectionComponent.StorageState.Configured
                    } else {
                        DirectorySelectionComponent.StorageState.NeedConfiguration
                    }
                }
            }
        }
    }

    override fun openDirectoryDialog() {
        _showDirectoryDialog.update { true }
        scope.launch {
            withContext(ioContext) {
                val selectedPath = localStorageDataSource.selectRootDirectory()
                if (selectedPath != null) {
                    verifyDirectory(selectedPath)
                }
                _showDirectoryDialog.update { false }
            }
        }
    }

    private suspend fun verifyDirectory(path: String) {
        if (!localStorageDataSource.checkDirectoryAccess(path)) {
            _state.update {
                DirectorySelectionComponent.StorageState.Error("No read/write access to directory")
            }
            return
        }

        if (!localStorageDataSource.isDirectoryEmpty(path)) {
            _state.update {
                DirectorySelectionComponent.StorageState.Error("Directory is not empty")
            }
            return
        }

        if (!localStorageDataSource.containsAppFiles(path)) {
            _state.update {
                DirectorySelectionComponent.StorageState.Error("Directory doesn't contain application files")
            }
            return
        }

        confirmDirectorySelection(path)
    }

    override fun closeDirectoryDialog() {
        _showDirectoryDialog.update { false }
    }

    override fun confirmDirectorySelection(directory: String) {
        scope.launch {
            withContext(ioContext)
            {
                val success = selectStorageLocation(directory)
                _state.update {
                    if (success) {
                        onFinished()
                        DirectorySelectionComponent.StorageState.Configured
                    } else {
                        DirectorySelectionComponent.StorageState.Error("Failed to save directory path")
                    }
                }
                closeDirectoryDialog()
            }
        }
    }
}