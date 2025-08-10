package com.notebook.nioapp.presentation.storageConfigurationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.notebook.nioapp.domain.repository.DirectorySelectionComponent
import com.notebook.nioapp.presentation.common.CirclesProgressIndicator
import com.notebook.nioapp.presentation.common.FilePathPresenter

@Composable
fun StorageConfigurationScreen(
    component: DirectorySelectionComponent,
    modifier: Modifier = Modifier
) {

    val state by component.state.subscribeAsState()
    val rootPath by component.rootPath.subscribeAsState()
    val checkResult by component.checkPathResult.subscribeAsState()
    Scaffold {
        when (state) {
            is DirectorySelectionComponent.StorageState.NeedConfiguration -> {
                StorageConfigurationComponent(
                    "Please select a directory for your notes",
                    rootPath,
                    checkResult,
                    component::openDirectoryDialog
                )
            }

            is DirectorySelectionComponent.StorageState.Configured -> {
                StorageConfigurationComponent(
                    "Folder is selected",
                    rootPath,
                    checkResult,
                    component::openDirectoryDialog,
                    component::confirmDirectorySelection
                )
            }

            is DirectorySelectionComponent.StorageState.Error -> {
                StorageConfigurationComponent(
                    (state as DirectorySelectionComponent.StorageState.Error).message, rootPath, checkResult,
                    component::openDirectoryDialog
                )
            }

            is DirectorySelectionComponent.StorageState.Loading -> {
                StorageConfigurationComponent(
                    "Loading",
                    rootPath,
                    checkResult,
                    component::openDirectoryDialog
                )
            }
        }
    }
}

@Composable
fun StorageConfigurationComponent(
    label: String = "",
    filePath: String? = null,
    checkResult: Boolean = false,
    openFolderClick: () -> Unit = {},
    continueClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.Gray)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .offset(y = (-16).dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(corner = CornerSize(8.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(36.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            label,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        FilePathPresenter(
                            filePath = filePath,
                            checkResult = checkResult,
                            openFolderClick = openFolderClick
                        )

                        Button(
                            onClick = continueClick,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                            enabled = checkResult
                        ) {
                            Text("Continue")
                        }
                    }
                }
            }
        }
    }
}