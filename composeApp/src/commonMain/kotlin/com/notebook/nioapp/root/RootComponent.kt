package com.notebook.nioapp.root


import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.notebook.nioapp.domain.repository.DirectorySelectionComponent
import com.notebook.nioapp.domain.repository.OnBoardingComponent
import kotlinx.serialization.Serializable
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class DirectorySelectionChild(val component: DirectorySelectionComponent) : Child()

        class OnBoarding(val component: OnBoardingComponent) : Child()

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
            initialConfiguration = Config.OnBoarding,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.DirectorySelection -> {
                RootComponent.Child.DirectorySelectionChild(directorySelectionComponent(componentContext))
            }

            is Config.OnBoarding -> RootComponent.Child.OnBoarding(onBoarding(componentContext))
        }

    @OptIn(DelicateDecomposeApi::class)
    private fun directorySelectionComponent(componentContext: ComponentContext): DirectorySelectionComponent =
        getKoin().get<DirectorySelectionComponent> {
            parametersOf(componentContext, {

            })
        }

    @OptIn(DelicateDecomposeApi::class)
    private fun onBoarding(componentContext: ComponentContext): OnBoardingComponent =
        getKoin().get<OnBoardingComponent> {
            parametersOf(componentContext, {
                navigation.push(Config.DirectorySelection)
            })
        }


    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object DirectorySelection : Config

        @Serializable
        data object OnBoarding : Config

    }
}