package com.notebook.nioapp.data.remote.impl

import com.notebook.nioapp.data.remote.api.CheckUrlApi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

internal class KtorCheckUrlApi(
    private val client: HttpClient
): CheckUrlApi {
    override suspend fun checkUrl(url: String): String? {
        return try {
            val response = client.get("${url.removeSuffix("/")}/url_check")
            response.status == HttpStatusCode.OK && response.body<Boolean>()
            "true"
        } catch (e: Exception) {
            "false"
        }
    }
}