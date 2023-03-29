package me.varoa.nikkedb.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import me.varoa.nikkedb.ui.navigation.NavGraph
import me.varoa.nikkedb.ui.theme.AppTheme

@Composable
fun NikkeDbApp() {
    val viewModel = hiltViewModel<AppViewModel>()
    val darkMode = viewModel.isDarkMode.collectAsState(isSystemInDarkTheme())

    AppTheme(
        useDarkTheme = darkMode.value
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            NavGraph()
        }
    }
}
