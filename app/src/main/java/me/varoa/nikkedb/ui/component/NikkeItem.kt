package me.varoa.nikkedb.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.varoa.nikkedb.core.data.dummyNikke
import me.varoa.nikkedb.core.domain.model.Nikke
import me.varoa.nikkedb.core.domain.model.generateSmallImageUrl
import me.varoa.nikkedb.ui.theme.AppTheme

@Composable
fun NikkeItem(
    nikke: Nikke,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { navigateToDetail(nikke.url) }
            .wrapContentSize()
            .semantics {
                contentDescription = "Nikke Item"
            }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = nikke.generateSmallImageUrl(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(144.dp)
                        .padding(top = 16.dp, bottom = 8.dp)
                )
                Text(
                    text = nikke.name,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Preview("NikkeItem")
@Preview("NikkeItem", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewNikkeItem() {
    AppTheme {
        NikkeItem(
            nikke = dummyNikke(),
            navigateToDetail = {}
        )
    }
}
