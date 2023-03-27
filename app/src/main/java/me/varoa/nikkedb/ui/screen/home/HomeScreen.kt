package me.varoa.nikkedb.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.varoa.nikkedb.R
import me.varoa.nikkedb.core.domain.model.Nikke
import me.varoa.nikkedb.ui.common.UiState
import me.varoa.nikkedb.ui.component.ErrorLayout
import me.varoa.nikkedb.ui.component.LazyListNikke
import me.varoa.nikkedb.ui.component.LoadingLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  modifier: Modifier = Modifier,
  viewModel: HomeViewModel,
  navigateToDetail: (String) -> Unit,
  navigateToFavorite: () -> Unit,
  navigateToAbout: () -> Unit,
) {
  val isDarkMode by viewModel.isDarkMode.collectAsState(true)
  val uiState by viewModel.uiState.collectAsState(UiState.Loading)
  val query by viewModel.query.collectAsState("")

  Scaffold(
    modifier = modifier,
    bottomBar = {
      BottomAppBar(
        actions = {
          IconButton(
            onClick = { navigateToFavorite() }
          ) {
            Icon(
              painter = painterResource(R.drawable.ic_heart),
              contentDescription = null
            )
          }
          IconButton(
            onClick = { viewModel.toggleTheme() }
          ) {
            if (isDarkMode) {
              Icon(
                painter = painterResource(R.drawable.ic_moon),
                contentDescription = null
              )
            } else {
              Icon(
                painter = painterResource(R.drawable.ic_sun),
                contentDescription = null
              )
            }
          }
          IconButton(
            onClick = { navigateToAbout() }
          ) {
            Icon(
              painter = painterResource(R.drawable.ic_info),
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
          HomeContent(
            data = state.data,
            query = query,
            onQueryChange = { viewModel.setQuery(it) },
            onSearch = {
              viewModel.searchNikke()
            },
            navigateToDetail = navigateToDetail,
            modifier = Modifier.padding(innerPadding),
          )
        }
        is UiState.Error -> {
          ErrorLayout(
            emoji = "🙏",
            message = state.errorMessage,
            onRetry = { viewModel.getAllNikkes() },
            modifier = Modifier.padding(innerPadding),
          )
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
  modifier: Modifier = Modifier,
  data: List<Nikke>,
  query: String,
  onQueryChange: (String) -> Unit,
  onSearch: (String) -> Unit,
  navigateToDetail: (String) -> Unit,
) {
  Box(
    modifier = modifier.fillMaxSize()
  ) {
    Column {
      SearchBar(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        query = query,
        active = false,
        onActiveChange = { },
        onQueryChange = { onQueryChange(it) },
        onSearch = {
          onSearch(it)
        },
        placeholder = { Text("Search NIKKE by name") },
        leadingIcon = {
          Icon(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = null
          )
        },
        trailingIcon = {
          if (query.isNotEmpty()) {
            IconButton(
              onClick = {
                onQueryChange("")
                onSearch("")
              }
            ) {
              Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = null,
              )
            }
          }
        }
      ) {}
      LazyListNikke(
        data = data,
        navigateToDetail = navigateToDetail,
      )
    }
  }
}