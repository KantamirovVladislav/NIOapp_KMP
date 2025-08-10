package com.notebook.nioapp.domain.repository

import com.arkivanov.decompose.value.Value

interface UrlSelectionComponent {

    val state: Value<UrlState>

    val url: Value<String>

    val checkUrlResult: Value<Boolean>

    fun checkUrl()

    fun confirmUrlSelection()

    fun confirmWithoutUrl()

    fun onUrlChange(url: String)

    sealed class UrlState {
        object Loading : UrlState()
        object NeedConfiguration : UrlState()
        object Configured : UrlState()
        data class Error(val message: String) : UrlState()
    }
}