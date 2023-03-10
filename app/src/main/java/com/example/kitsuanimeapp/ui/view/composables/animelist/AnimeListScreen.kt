@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kitsuanimeapp.ui.view.composables.animelist

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto.AnimeResponseData
import com.example.kitsuanimeapp.ui.view.composables.common.BodyText
import com.example.kitsuanimeapp.ui.view.composables.common.TitleText

private const val TAG = "AnimeListScreen"

@Composable
fun AnimeListScreen(
    currentList: List<AnimeResponseData>,
    onAnimeSelect: (animeData: AnimeResponseData) -> Unit,
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(currentList) { animeData ->
            AnimeListItem(animeData) {
                Log.e(
                    TAG,
                    "AnimeListScreen: selected anime was ${animeData.attributes.canonicalTitle}",
                )
                onAnimeSelect(animeData)
            }
        }
    }
}

@Composable
fun AnimeListItem(
    animeData: AnimeResponseData,
    onAnimeSelected: () -> Unit,
) {
    val title = animeData.attributes.canonicalTitle
    var showAlert by remember { mutableStateOf(false) }
    var isEnabled by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Black),
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(8.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp),
            ) {
                AsyncImage(
                    model = animeData.attributes.posterImage.large,
                    contentDescription = "This is an image from $title",
                    modifier = Modifier
                        .widthIn(min = 100.dp, max = 150.dp)
                        .heightIn(min = 150.dp, max = 200.dp),
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp),
                ) {
                    TitleText(text = title)
                    BodyText(text = animeData.attributes.synopsis) {
                        // GO TO DETAILS OF THIS ANIME.
                        showAlert = !showAlert
                    }
                }
            }
            if (showAlert) {
                AlertDialog(
                    onDismissRequest = {
                        showAlert = !showAlert
                    },
                    modifier = Modifier.clickable {
                        showAlert = false
                    },
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Text(
                            text = animeData.attributes.synopsis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }

            Button(
                onClick = {
                    Log.e(TAG, "AnimeListItem: clicked the read me button")
                    onAnimeSelected()
                    isEnabled = !isEnabled
                },
                enabled = isEnabled,
            ) {
                Text(text = "Read more ->")
            }
        }
    }
}
