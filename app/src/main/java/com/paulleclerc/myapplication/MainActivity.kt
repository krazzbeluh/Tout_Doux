package com.paulleclerc.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.paulleclerc.myapplication.ui.add.AddTodoScreen
import com.paulleclerc.myapplication.ui.list.ListTodosScreen
import com.paulleclerc.myapplication.ui.navigation.Screen
import com.paulleclerc.myapplication.ui.theme.MyApplicationTheme

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Navigation()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun Navigation() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(
            route = Screen.MainScreen.route,) {
            ListTodosScreen(
                onConfirm = { navController.navigate(Screen.AddScreen.route) })
        }
        composable(
            route = Screen.AddScreen.route,
            enterTransition = { _, _ ->
                slideInVertically(animationSpec = tween(500),
                initialOffsetY = { fullHeight -> fullHeight })
            },
            exitTransition = { _, _ ->
                slideOutVertically(animationSpec = tween(500),
                targetOffsetY = { fullHeight -> fullHeight })
            }) {
            AddTodoScreen(
                onConfirm = { navController.navigateUp() })
        }
    }
}
