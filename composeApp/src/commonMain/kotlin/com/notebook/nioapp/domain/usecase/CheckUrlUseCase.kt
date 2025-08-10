package com.notebook.nioapp.domain.usecase

import com.notebook.nioapp.domain.repository.SettingsDataSource
import com.notebook.nioapp.domain.repository.remote.CheckUrlRepository


class CheckUrlUseCase(
    private val repository: CheckUrlRepository,
    private val appSettings: SettingsDataSource
) {
    suspend operator fun invoke(url: String): String? {
        val isValid = repository.checkUrl(url)
        if (isValid == "true") {
            appSettings.saveUrl(url)
        }
        return isValid
    }
}