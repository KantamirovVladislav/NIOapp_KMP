package com.notebook.nioapp.presentation.viewmodel

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.notebook.nioapp.domain.repository.OnBoardingComponent

class DefaultOnBoardingComponent(
    componentContext: ComponentContext,
    private val onFinished: () -> Unit
) : OnBoardingComponent, ComponentContext by componentContext {

    private val _currentPage = MutableValue(0)
    override val currentPage: Value<Int> = _currentPage

    private val totalPages = 3

    override fun onNextClicked() {
        if (_currentPage.value < totalPages - 1) {
            _currentPage.value += 1
        } else {
            onFinished()
        }
    }

    override fun onBackClicked() {
        if (_currentPage.value > 0) {
            _currentPage.value -= 1
        }

    }

    override fun onPageChanged(page: Int) {
        if (_currentPage.value != page) {
            _currentPage.value = page
        }
    }
}