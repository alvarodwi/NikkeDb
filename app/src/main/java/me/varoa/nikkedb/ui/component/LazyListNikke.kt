package me.varoa.nikkedb.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import me.varoa.nikkedb.core.domain.model.Nikke

@Composable
fun LazyListNikke(
  data: List<Nikke>,
  navigateToDetail: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  if (data.isEmpty()) {
    ErrorLayout(
      emoji = "🤌",
      message = "There is nothing here",
      shouldShowRetry = false,
      onRetry = {},
    )
  } else {
    LazyVerticalGrid(
      columns = Adaptive(160.dp),
      contentPadding = PaddingValues(16.dp),
      horizontalArrangement = Arrangement.spacedBy(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      modifier = modifier.semantics{
        contentDescription = "List Nikke"
      }
    ) {
      items(data) { data ->
        NikkeItem(
          nikke = data,
          navigateToDetail = navigateToDetail
        )
      }
    }
  }
}