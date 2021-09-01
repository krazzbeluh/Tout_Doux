package com.paulleclerc.myapplication.ui.navigation

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object AddScreen : Screen("add_screen")

    fun withArgs(vararg args: String): String = buildString {
        append(route)
        args.forEach { arg ->
            append("/$arg")
        }
    }
}

