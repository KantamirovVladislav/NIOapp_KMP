import org.gradle.kotlin.dsl.invoke
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinx.serialization) apply true
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation("io.insert-koin:koin-android:3.5.3")
            implementation("io.insert-koin:koin-androidx-compose:3.5.3")
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation("io.insert-koin:koin-core:3.5.3")
            implementation(libs.ktor.client.core)
            implementation("com.russhwolf:multiplatform-settings:1.3.0")
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")
            implementation("com.arkivanov.decompose:decompose:3.3.0")
            implementation("com.arkivanov.decompose:extensions-compose:3.3.0")
            implementation("com.arkivanov.essenty:lifecycle:2.5.0")
            implementation("com.arkivanov.essenty:lifecycle-coroutines:2.5.0")

            implementation("dev.icerock.moko:resources:0.25.0")
            implementation("dev.icerock.moko:resources-compose:0.25.0")

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation("dev.icerock.moko:resources-test:0.25.0")
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")
        }
        iosMain.dependencies {
            implementation("io.insert-koin:koin-core:3.5.3")
            implementation(libs.ktor.client.darwin)
        }

        jvmMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")
        }
    }
}

android {
    namespace = "com.notebook.nioapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    lint {
        disable.add("NullSafeMutableLiveData")
    }
    defaultConfig {
        applicationId = "com.notebook.nioapp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    android {
        configurations {
            all {
                exclude(group = "androidx.compose.ui", module = "ui-util-desktop")
                exclude(group = "com.google.guava", module = "listenablefuture")
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.compiler)
    implementation(libs.androidx.datastore.preferences.core.jvm)
    implementation(libs.androidx.ui.util.desktop)
    implementation(libs.androidx.navigation.runtime.android)
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.notebook.nioapp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.notebook.nioapp"
            packageVersion = "1.0.0"
        }
    }
}
