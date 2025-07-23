package com.notebook.nioapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform