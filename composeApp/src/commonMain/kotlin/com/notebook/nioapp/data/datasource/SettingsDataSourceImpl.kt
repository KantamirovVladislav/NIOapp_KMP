package com.notebook.nioapp.data.datasource

import com.notebook.nioapp.domain.repository.SettingsDataSource
import com.russhwolf.settings.Settings

class SettingsDataSourceImpl(settings: Settings) : SettingsDataSource {
    private companion object {
        const val ROOT_PATH_KEY = "root_path"
        const val URL_KEY = "url"
    }
    private val _settings: Settings = settings

    override fun getRootPath(): String? {
        return _settings.getStringOrNull(ROOT_PATH_KEY)
    }

    override fun saveRootPath(path: String) {
        _settings.putString(ROOT_PATH_KEY, path)
    }

    override fun getUrl(): String? {
        return _settings.getStringOrNull(URL_KEY)
    }

    override fun saveUrl(url: String) {
        _settings.putString(URL_KEY, url)
    }

    override fun clearStorage() {
        _settings.remove(ROOT_PATH_KEY)
    }
}