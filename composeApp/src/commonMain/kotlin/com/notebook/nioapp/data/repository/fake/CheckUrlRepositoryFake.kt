package com.notebook.nioapp.data.repository.fake

import com.notebook.nioapp.domain.repository.remote.CheckUrlRepository


internal class CheckUrlRepositoryFake : CheckUrlRepository {
    override suspend fun checkUrl(url: String): String? = "true"
}