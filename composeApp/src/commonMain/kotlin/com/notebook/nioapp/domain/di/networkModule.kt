package com.notebook.nioapp.domain.di

import com.notebook.nioapp.data.remote.api.CheckUrlApi
import com.notebook.nioapp.data.remote.impl.KtorCheckUrlApi
import com.notebook.nioapp.data.repository.CheckUrlRepositoryImpl
import com.notebook.nioapp.data.repository.fake.CheckUrlRepositoryFake
import com.notebook.nioapp.domain.repository.remote.CheckUrlRepository
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single<CheckUrlApi> {
        KtorCheckUrlApi(client = get())
    }

    single<CheckUrlRepository> {
        CheckUrlRepositoryFake()
    }

    single {
        HttpClient {
            install(HttpTimeout) {
                requestTimeoutMillis = 20_000
                connectTimeoutMillis = 10_000
                socketTimeoutMillis = 30_000
            }


            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    explicitNulls = false
                })
            }

            defaultRequest {
                header("Content-Type", "application/json")
                header("Accept", "application/json")
            }
        }
    }
}