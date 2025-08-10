package com.notebook.nioapp.data.repository

import com.notebook.nioapp.data.remote.api.CheckUrlApi
import com.notebook.nioapp.domain.repository.remote.CheckUrlRepository

internal class CheckUrlRepositoryImpl(
    private val checkUrlApi: CheckUrlApi
): CheckUrlRepository {
    override suspend fun checkUrl(url: String): String? = checkUrlApi.checkUrl(url)
}