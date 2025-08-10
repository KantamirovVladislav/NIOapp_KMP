package com.notebook.nioapp.domain.repository

expect class DirectoryChooser {
    fun showDialog(): String?
}