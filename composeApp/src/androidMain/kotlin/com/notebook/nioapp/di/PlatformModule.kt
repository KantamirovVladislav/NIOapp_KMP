package com.notebook.nioapp.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidPlatformModule = module {
    single<Settings> {
        SharedPreferencesSettings(
            delegate = androidContext().getSharedPreferences(
                "AppSettings",
                Context.MODE_PRIVATE
            )
        )
    }
}