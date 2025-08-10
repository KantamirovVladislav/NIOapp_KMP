package com.notebook.nioapp.data.remote.api

internal interface CheckUrlApi {
    suspend fun checkUrl(url: String): String?
}