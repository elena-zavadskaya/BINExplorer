package com.example.binexplorer.presentation.navigation

sealed class ScreenRoute(val route: String) {
    object SEARCH : ScreenRoute("search")
    object HISTORY : ScreenRoute("history")
}