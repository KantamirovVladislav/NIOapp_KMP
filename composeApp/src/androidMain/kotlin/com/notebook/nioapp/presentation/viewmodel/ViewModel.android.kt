package com.notebook.nioapp.presentation.viewmodel

actual abstract class ViewModel actual constructor(): androidx.lifecycle.ViewModel() {
    actual fun clear() {
        this.clear()
    }

    actual override fun onCleared() {
        this.onCleared()
    }
}