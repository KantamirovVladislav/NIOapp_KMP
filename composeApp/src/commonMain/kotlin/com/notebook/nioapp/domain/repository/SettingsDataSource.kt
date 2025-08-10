package com.notebook.nioapp.domain.repository

interface SettingsDataSource {
    fun getRootPath(): String?
    fun saveRootPath(path: String)

    fun getUrl(): String?

    fun saveUrl(url: String)

    fun clearStorage()
}