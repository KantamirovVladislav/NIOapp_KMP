package com.notebook.nioapp.presentation.viewmodel

import androidx.lifecycle.asLiveData

class AndroidStorageViewModel(
    private val viewModel: StorageViewModel
) : androidx.lifecycle.ViewModel() {
    val state = viewModel.state

    fun configureStorage() {
        //viewModel.configureStorage()
    }

    override fun onCleared() {
        viewModel.clear()
    }
}