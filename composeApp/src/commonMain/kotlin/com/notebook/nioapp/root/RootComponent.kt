package com.notebook.nioapp.root


import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.webhistory.WebNavigationOwner
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.notebook.nioapp.domain.repository.DirectorySelectionComponent
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked(toIndex: Int)

    sealed class Child {
        // data object AuthChild : Child()
        class DirectorySelectionChild(val component: DirectorySelectionComponent) : Child()
        // data object UrlSelectionChild : Child()
    }
}

class RootComponentImpl(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.DirectorySelection,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            //is Config.Auth -> {}
            is Config.DirectorySelection -> {
                RootComponent.Child.DirectorySelectionChild(directorySelectionComponent(componentContext))
            }
            //is Config.UrlSelection -> {}
        }

    private fun directorySelectionComponent(componentContext: ComponentContext): DirectorySelectionComponent =
        getKoin().get<DirectorySelectionComponent> {
            parametersOf(componentContext, { isSuccess: Boolean ->

            })
        }


    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    @Serializable
    private sealed interface Config {
//        @Serializable
//        data object Auth : Config

        @Serializable
        data object DirectorySelection : Config


//        @Serializable
//        data object UrlSelection : Config

    }
}