package com.notebook.nioapp.presentation.urlSelectionScreen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.notebook.nioapp.domain.repository.DefaultComponent
import com.notebook.nioapp.domain.repository.DirectorySelectionComponent
import com.notebook.nioapp.domain.repository.UrlSelectionComponent
import com.notebook.nioapp.domain.usecase.CheckUrlUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultUrlSelectionComponent(
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    ioContext: CoroutineContext,
    private val checkUrlUseCase: CheckUrlUseCase,
    private val onFinished: () -> Unit,
) : UrlSelectionComponent, DefaultComponent(componentContext, mainContext = mainContext, ioContext = ioContext) {

    private val _state = MutableValue<UrlSelectionComponent.UrlState>(UrlSelectionComponent.UrlState.Loading)
    override val state: Value<UrlSelectionComponent.UrlState> = _state

    private val _url = MutableValue("https://")
    override val url: Value<String> = _url

    private val _checkUrlResult = MutableValue(false)
    override val checkUrlResult: Value<Boolean> = _checkUrlResult

    override fun checkUrl() {
        scope.launch {
            _state.update { UrlSelectionComponent.UrlState.Loading }
            withContext(ioContext){
                val result = checkUrlUseCase(_url.value)
                _checkUrlResult.value = result == "true"
            }
            if (_checkUrlResult.value) {
                _state.update { UrlSelectionComponent.UrlState.Configured }
            } else {
                _state.update { UrlSelectionComponent.UrlState.NeedConfiguration }
            }
        }
    }

    override fun confirmUrlSelection() {
       scope.launch {
           onFinished()
       }
    }

    override fun confirmWithoutUrl() {
        scope.launch {
            onFinished()
        }
    }

    override fun onUrlChange(url: String) {
        _url.value = url
        checkUrl()
    }
}