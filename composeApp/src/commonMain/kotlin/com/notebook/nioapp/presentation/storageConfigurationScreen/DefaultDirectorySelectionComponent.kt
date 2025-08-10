package com.notebook.nioapp.presentation.storageConfigurationScreen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.notebook.nioapp.domain.repository.DefaultComponent
import com.notebook.nioapp.domain.repository.DirectoryChooser
import com.notebook.nioapp.domain.repository.DirectorySelectionComponent
import com.notebook.nioapp.domain.repository.FileSystem
import com.notebook.nioapp.domain.usecase.InitialStorageLocation
import com.notebook.nioapp.domain.usecase.SelectStorageLocationUseCase
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultDirectorySelectionComponent(
    componentContext: ComponentContext,
    private val selectStorageLocation: SelectStorageLocationUseCase,
    private val initializeStorage: InitialStorageLocation,
    private val localStorageDataSource: FileSystem,
    private val directoryChooser: DirectoryChooser,
    private val onFinished: () -> Unit,
    mainContext: CoroutineContext,
    ioContext: CoroutineContext
) : DirectorySelectionComponent, DefaultComponent(componentContext, mainContext = mainContext, ioContext = ioContext) {
    private val _state =
        MutableValue<DirectorySelectionComponent.StorageState>(DirectorySelectionComponent.StorageState.Loading)
    override val state: Value<DirectorySelectionComponent.StorageState> = _state

    private val _rootPath =
        MutableValue<String>("")
    override val rootPath: Value<String> = _rootPath

    private val _checkPathResult =
        MutableValue<Boolean>(false)

    override val checkPathResult: Value<Boolean> = _checkPathResult

    init {
        checkStorageConfiguration()
    }

    private fun checkStorageConfiguration() {
        scope.launch {
            withContext(ioContext)
            {
                val isConfigured = initializeStorage()

                if (isConfigured.isNullOrEmpty()) {
                    println("null")
                    _state.update { DirectorySelectionComponent.StorageState.NeedConfiguration }
                } else {
                    println("not null")
                    verifyDirectory(isConfigured)
                }
            }
        }
    }


    override fun openDirectoryDialog() {
        scope.launch {
            withContext(ioContext) {
                val selectedPath = directoryChooser.showDialog()
                if (selectedPath != null) {
                    verifyDirectory(selectedPath)
                }
            }
        }
    }

    private suspend fun verifyDirectory(path: String) {
        _rootPath.value = path
        if (!localStorageDataSource.checkDirectoryAccess(path)) {
            _state.update {
                DirectorySelectionComponent.StorageState.Error("No read/write access to directory")
            }
            _checkPathResult.value = false
            return
        }

        if (!localStorageDataSource.isDirectoryEmpty(path)) {
            _state.update {
                DirectorySelectionComponent.StorageState.Error("Directory is not empty")
            }
            _checkPathResult.value = false
            return
        }

        if (!localStorageDataSource.containsAppFiles(path, emptyList())) {
            _state.update {
                DirectorySelectionComponent.StorageState.Error("Directory doesn't contain application files")
            }
            _checkPathResult.value = false
            return
        }
        _state.update { DirectorySelectionComponent.StorageState.Configured }
        _checkPathResult.value = true
    }

    override fun confirmDirectorySelection() {
        scope.launch {
            val success = selectStorageLocation(_rootPath.value)
            println("$success - ${_rootPath.value}")
            _state.update {
                if (success) {
                    onFinished()
                    DirectorySelectionComponent.StorageState.Configured
                } else {
                    DirectorySelectionComponent.StorageState.Error("Failed to save directory path")
                }
            }
        }
    }
}