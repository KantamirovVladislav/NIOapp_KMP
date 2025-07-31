package com.notebook.nioapp.presentation.viewmodel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.notebook.nioapp.data.datasource.LocalStorageDataSource
import com.notebook.nioapp.domain.repository.DirectorySelectionComponent

@Composable
fun StorageConfigurationScreen(
    component: DirectorySelectionComponent,
    modifier: Modifier = Modifier
) {

    val state by component.state.subscribeAsState()

    when (state) {
        is DirectorySelectionComponent.StorageState.NeedConfiguration -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Please select a directory for your notes",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = component::openDirectoryDialog,
                    modifier = Modifier.width(200.dp)
                ) {
                    Text("Select Directory")
                }
            }
        }

        is DirectorySelectionComponent.StorageState.Configured -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Configured",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = component::openDirectoryDialog,
                    modifier = Modifier.width(200.dp)
                ) {
                    Text("Select Directory")
                }
            }
        }
        is DirectorySelectionComponent.StorageState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    (state as DirectorySelectionComponent.StorageState.Error).message,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = component::openDirectoryDialog,
                    modifier = Modifier.width(200.dp)
                ) {
                    Text("Select Directory")
                }
            }
        }
        is DirectorySelectionComponent.StorageState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Loading",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = component::openDirectoryDialog,
                    modifier = Modifier.width(200.dp)
                ) {
                    Text("Select Directory")
                }
            }
        }
    }
}