package com.example.kitsuanimeapp.ui.view.composables.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO.Attributes
import com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO.CategoryData
import com.example.kitsuanimeapp.ui.view.composables.common.BodyText
import com.example.kitsuanimeapp.ui.view.composables.common.MediaCount
import com.example.kitsuanimeapp.ui.view.composables.common.NSFWTag
import com.example.kitsuanimeapp.ui.view.composables.common.TitleText
import com.example.kitsuanimeapp.util.PAGE_PERCENTAGE

@Composable
fun CategoryScreen(
    categories: List<CategoryData>,
    onItemSelected: (data: CategoryData) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(categories) {
            CategoryItem(categoryAttributes = it.attributes) {
                onItemSelected(it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItem(categoryAttributes: Attributes, onItemSelected: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(PAGE_PERCENTAGE)
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface),
        onClick = onItemSelected,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp,
            hoveredElevation = 32.dp,
            pressedElevation = 8.dp,
        ),
        border = BorderStroke(1.dp, Color.Black),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            ) {
                TitleText(text = categoryAttributes.title)
                if (categoryAttributes.nsfw) {
                    NSFWTag()
                }
                MediaCount(count = categoryAttributes.totalMediaCount)
            }
            if (categoryAttributes.description.isNotEmpty()) {
                Divider(
                    Modifier
                        .fillMaxWidth(PAGE_PERCENTAGE)
                        .padding(bottom = 16.dp, top = 16.dp),
                )
                Row(Modifier.padding(12.dp)) {
                    BodyText(text = categoryAttributes.description) {}
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun CategoryItemPreview() {
    NSFWTag()
}
