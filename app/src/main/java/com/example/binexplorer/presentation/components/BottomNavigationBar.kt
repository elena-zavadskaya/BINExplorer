package com.example.binexplorer.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.binexplorer.R
import com.example.binexplorer.presentation.navigation.ScreenRoute

@Composable
fun BottomNavigationBar(
    currentRoute: ScreenRoute,
    navigateToSearch: () -> Unit,
    navigateToHistory: () -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search)
                )
            },
            label = { Text(stringResource(R.string.search)) },
            selected = currentRoute == ScreenRoute.SEARCH,
            onClick = navigateToSearch
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = stringResource(R.string.history)
                )
            },
            label = { Text(stringResource(R.string.history)) },
            selected = currentRoute == ScreenRoute.HISTORY,
            onClick = navigateToHistory
        )
    }
}