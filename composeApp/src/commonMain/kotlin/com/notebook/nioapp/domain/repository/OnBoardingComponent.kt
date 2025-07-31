package com.notebook.nioapp.domain.repository

import com.arkivanov.decompose.value.Value

interface OnBoardingComponent {
    val currentPage: Value<Int>

    fun onNextClicked()

    fun onBackClicked()

    fun onPageChanged(page: Int)
}