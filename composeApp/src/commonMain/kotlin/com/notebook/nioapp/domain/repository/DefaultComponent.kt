package com.notebook.nioapp.domain.repository

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

open class DefaultComponent(
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    protected val ioContext: CoroutineContext,
) : ComponentContext by componentContext {
    protected val scope = coroutineScope(mainContext + SupervisorJob())
}