package com.notebook.nioapp.data.datasource

import com.russhwolf.settings.Settings

class SettingsDataSourceImpl(settings: Settings) : SettingsDataSource {
    private companion object {
        const val ROOT_PATH_KEY = "root_path"
    }
    private val _settings: Settings = settings

    override fun getRootPath(): String? {
        return _settings.getStringOrNull(ROOT_PATH_KEY)
    }

    override fun saveRootPath(path: String) {
        _settings.putString(ROOT_PATH_KEY, path)
    }

    override fun clearStorage() {
        _settings.remove(ROOT_PATH_KEY)
    }
}