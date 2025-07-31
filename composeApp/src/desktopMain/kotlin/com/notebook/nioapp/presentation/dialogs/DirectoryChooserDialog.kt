package com.notebook.nioapp.presentation.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.io.File

@Composable
fun DirectoryChooserDialog(
    initialDirectory: File? = null,
    onDirectorySelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var currentDir by remember { mutableStateOf(initialDirectory ?: File(System.getProperty("user.home"))) }
    var dirContent by remember { mutableStateOf<List<File>?>(null) }

    LaunchedEffect(currentDir) {
        dirContent = currentDir.listFiles()?.filter { it.isDirectory }?.sorted()
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.width(400.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Select Directory",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Current path
                Text(
                    text = currentDir.absolutePath,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Parent directory button
                if (currentDir.parentFile != null) {
                    Button(
                        onClick = { currentDir = currentDir.parentFile },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Go Up (${currentDir.parentFile?.name ?: "Root"})")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Directory list
                LazyColumn(modifier = Modifier.heightIn(max = 300.dp)) {
                    dirContent?.let { directories ->
                        items(directories) { dir ->
                            DirectoryItem(
                                directory = dir,
                                onClick = { currentDir = dir }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onDismiss
                    ) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onDirectorySelected(currentDir.absolutePath) },
                        enabled = currentDir.canWrite()
                    ) {
                        Text("Select")
                    }
                }
            }
        }
    }
}

@Composable
private fun DirectoryItem(
    directory: File,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        TextButton(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = directory.name,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )

        }
    }
}