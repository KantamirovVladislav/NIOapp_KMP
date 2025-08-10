package com.notebook.nioapp.domain.repository.remote

interface CheckUrlRepository {
    suspend fun checkUrl(url: String): String?
}