package com.example.binexplorer

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.binexplorer.presentation.navigation.ScreenRoute
import com.example.binexplorer.presentation.screens.BinLookupScreen
import com.example.binexplorer.presentation.screens.HistoryScreen
import com.example.binexplorer.presentation.ui.theme.BINExplorerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            BINExplorerTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = ScreenRoute.SEARCH.route,
                    modifier = Modifier
                ) {
                    composable(
                        route = ScreenRoute.SEARCH.route,
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(300)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(300)
                            )
                        }
                    ) {
                        BinLookupScreen(
                            onItemClick = { type, data -> handleItemClick(type, data) },
                            navigateToSearch = { /* Уже на этом экране */ },
                            navigateToHistory = {
                                navController.navigate(ScreenRoute.HISTORY.route) {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    composable(
                        route = ScreenRoute.HISTORY.route,
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(300)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(300)
                            )
                        }
                    ) {
                        HistoryScreen(
                            navigateToSearch = {
                                navController.navigate(ScreenRoute.SEARCH.route) {
                                    popUpTo(ScreenRoute.SEARCH.route) { inclusive = false }
                                    launchSingleTop = true
                                }
                            },
                            navigateToHistory = { /* Уже на этом экране */ }
                        )
                    }
                }
            }
        }
    }

    private fun handleItemClick(type: String, data: String) {
        val intent = when (type) {
            "url" -> Intent(Intent.ACTION_VIEW, Uri.parse(data))
            "tel" -> Intent(Intent.ACTION_DIAL, Uri.parse("tel:$data"))
            "geo" -> Intent(Intent.ACTION_VIEW, Uri.parse("geo:$data"))
            else -> null
        }

        intent?.let {
            try {
                startActivity(it)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Не найдено приложение для обработки", Toast.LENGTH_SHORT).show()
            }
        }
    }
}