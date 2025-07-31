package com.notebook.nioapp.data.datasource

interface SettingsDataSource {
    fun getRootPath(): String?
    fun saveRootPath(path: String)
    fun clearStorage()
}