package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.di.desktopModules
import org.koin.core.context.startKoin
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.MenuBar
import kotlin.system.exitProcess

fun main() = application {
    startKoin{
        modules(desktopModules)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "GUI_Controller",
    ) {
        val isOpen = remember { mutableStateOf(false) }
        MenuBar {
            Menu("Application") {
//                Item(text = "Open New Window", shortcut = KeyShortcut(Key.F1)) {
//                    isOpen.value = true
//                }
                Item(text = "Exit", shortcut = KeyShortcut(Key.F4, alt = true)) {
                    exitApplication()
                }
            }
            Menu("Help"){
                Item(text = "View") {

                }
            }
        }
        if(isOpen.value){
            OpenWindow(isOpen)
        }
        App()
    }

}

@Composable
fun OpenWindow(isOpen: MutableState<Boolean>) {
    Window(onCloseRequest = { isOpen.value = false },
        content = {},
        title = "New Window"
    )
}