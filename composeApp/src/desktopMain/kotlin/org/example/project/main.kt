package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.di.desktopModules
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun main() = application {
    startKoin{
        modules(desktopModules)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "GUI_Controller",
    ) {
        App()
    }
}