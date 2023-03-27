package me.varoa.nikkedb.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.varoa.nikkedb.ui.theme.AppTheme

@Composable
fun ErrorLayout(
  emoji: String,
  message: String,
  onRetry: () -> Unit,
  modifier: Modifier = Modifier,
  shouldShowRetry: Boolean = true,
) {
  Box(
    modifier = modifier.fillMaxSize()
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.align(Alignment.Center)
    ) {
      Text(
        text = emoji,
        fontSize = 96.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(bottom = 16.dp)
      )
      Text(
        text = message,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
      )
      if(shouldShowRetry){
        Button(
          onClick = onRetry,
          contentPadding = ButtonDefaults.TextButtonContentPadding,
        ) {
          Text("Retry")
        }
      }
    }
  }
}

@Preview("ErrorLayout")
@Preview("ErrorLayout", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewErrorLayout() {
  AppTheme {
    ErrorLayout(
      emoji = "🙏",
      message = "Something went wrong",
      onRetry = {},
      modifier = Modifier.background(MaterialTheme.colorScheme.background)
    )
  }
}