package com.notebook.nioapp.domain.repository

import javax.swing.JFileChooser

actual class DirectoryChooser {
    actual fun showDialog(): String? {
        val chooser = JFileChooser().apply {
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            dialogTitle = "Select Directory"
        }

        return when (chooser.showOpenDialog(null)) {
            JFileChooser.APPROVE_OPTION -> chooser.selectedFile.absolutePath
            else -> null
        }
    }
}