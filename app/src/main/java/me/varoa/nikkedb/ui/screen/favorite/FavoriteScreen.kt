package me.varoa.nikkedb.ui.screen.favorite

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import me.varoa.nikkedb.R.drawable
import me.varoa.nikkedb.ui.common.UiState
import me.varoa.nikkedb.ui.component.ErrorLayout
import me.varoa.nikkedb.ui.component.LazyListNikke
import me.varoa.nikkedb.ui.component.LoadingLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    navigateToDetail: (String) -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState(UiState.Loading)

    Scaffold(
        topBar =
        {
            TopAppBar(
                title = { Text("Favorite NIKKE") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            painter = painterResource(drawable.ic_arrow_left),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        uiState.let { state ->
            when (state) {
                is UiState.Loading -> {
                    LoadingLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                is UiState.Success -> {
                    LazyListNikke(
                        data = state.data,
                        navigateToDetail = navigateToDetail,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                is UiState.Error -> {
                    ErrorLayout(
                        emoji = "🙏",
                        message = state.errorMessage,
                        onRetry = { viewModel.fetchFavorites() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
