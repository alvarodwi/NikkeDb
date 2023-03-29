package me.varoa.nikkedb.ui.screen.detail

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import me.varoa.nikkedb.R
import me.varoa.nikkedb.core.data.dummyNikke
import me.varoa.nikkedb.core.domain.model.Nikke
import me.varoa.nikkedb.core.domain.model.generateFullImageUrl
import me.varoa.nikkedb.ui.common.UiState
import me.varoa.nikkedb.ui.component.ErrorLayout
import me.varoa.nikkedb.ui.component.LoadingLayout
import me.varoa.nikkedb.ui.theme.AppTheme
import me.varoa.nikkedb.ui.theme.nikkeR
import me.varoa.nikkedb.ui.theme.nikkeSr
import me.varoa.nikkedb.ui.theme.nikkeSsr

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
  viewModel: DetailViewModel,
  navigateBack: () -> Unit,
) {
  val uiState by viewModel.uiState.collectAsState(UiState.Loading)
  val isFavorite by viewModel.isFavorite.collectAsState(false)

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Detail NIKKE") },
        navigationIcon = {
          IconButton(onClick = navigateBack) {
            Icon(
              painter = painterResource(R.drawable.ic_arrow_left),
              contentDescription = null
            )
          }
        },
        actions = {
          IconButton(
            onClick = { viewModel.toggleFavorite() }
          ) {
            if (isFavorite) {
              Icon(
                painter = painterResource(R.drawable.ic_heart_filled),
                contentDescription = null
              )
            } else {
              Icon(
                painter = painterResource(R.drawable.ic_heart),
                contentDescription = null
              )
            }
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
          DetailContent(
            data = state.data,
            modifier = Modifier.padding(innerPadding)
          )
        }
        is UiState.Error -> {
          ErrorLayout(
            emoji = "🙏",
            message = state.errorMessage,
            onRetry = { viewModel.getNikkeDetail() },
            modifier = Modifier.padding(innerPadding),
          )
        }
      }
    }
  }
}

@Composable
fun DetailContent(
  data: Nikke,
  modifier: Modifier = Modifier,
) {
  ConstraintLayout(
    modifier = modifier
  ) {
    val (card, rarity, image) = createRefs()

    Image(
      painter = rememberAsyncImagePainter(data.generateFullImageUrl()),
      contentScale = ContentScale.FillHeight,
      alignment = Alignment.Center,
      contentDescription = null,
      modifier = Modifier.fillMaxSize()
        .constrainAs(image) {
          top.linkTo(parent.top)
          bottom.linkTo(parent.bottom)
          start.linkTo(parent.start)
          end.linkTo(parent.end)
        }
        .semantics {
          contentDescription = "Nikke Full Image"
        }
    )
    Text(
      text = data.rarity,
      style = MaterialTheme.typography.headlineMedium,
      color = when (data.rarity) {
        "SSR" -> nikkeSsr
        "SR" -> nikkeSr
        else -> nikkeR
      },
      fontWeight = FontWeight.ExtraBold,
      modifier = Modifier.constrainAs(rarity) {
        top.linkTo(parent.top, margin = 16.dp)
        start.linkTo(parent.start, margin = 16.dp)
      }.wrapContentSize()
    )
    Card(
      modifier = Modifier.constrainAs(card) {
        bottom.linkTo(parent.bottom)
      }.wrapContentSize()
        .semantics {
          contentDescription = "Nikke Info Card"
        }
    ) {
      Column(
        modifier = Modifier.padding(16.dp)
      ) {
        Text(
          text = data.name,
          style = MaterialTheme.typography.headlineMedium,
          fontWeight = FontWeight.Bold,
        )
        Text(
          text = "Manufacturer\t : ${data.manufacturer}",
          style = MaterialTheme.typography.bodySmall,
        )
        Text(
          text = "Squad\t\t\t\t\t\t : ${data.squad}",
          style = MaterialTheme.typography.bodySmall,
        )
        Text(
          text = "Weapon\t\t\t\t\t : ${data.weapon}",
          style = MaterialTheme.typography.bodySmall,
        )
        Text(
          text = "Class\t\t\t\t\t\t : ${data.classType}",
          style = MaterialTheme.typography.bodySmall,
        )
        Text(
          text = "Burst Type\t\t\t : Burst ${data.burst}",
          style = MaterialTheme.typography.bodySmall,
        )
        Divider(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
          color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        )
        Text(
          text = data.description,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
        )
      }
    }
  }
}

@Preview("DetailContent")
@Preview("DetailContent", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewDetailContent() {
  AppTheme {
    DetailContent(
      data = dummyNikke(),
    )
  }
}