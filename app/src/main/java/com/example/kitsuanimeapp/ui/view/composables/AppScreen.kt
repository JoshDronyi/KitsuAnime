package com.example.kitsuanimeapp.ui.view.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto.AnimeListAttributes
import com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto.AnimeResponseData
import com.example.kitsuanimeapp.ui.view.composables.animelist.AnimeListScreen
import com.example.kitsuanimeapp.ui.view.composables.category.CategoryScreen
import com.example.kitsuanimeapp.ui.view.composables.common.SubTitleText
import com.example.kitsuanimeapp.ui.view.composables.common.TitleText
import com.example.kitsuanimeapp.ui.viewmodels.AnimeListViewModel
import com.example.kitsuanimeapp.ui.viewmodels.CategoryViewModel
import com.example.kitsuanimeapp.util.DEFAULT_ANIME_STRING
import com.example.kitsuanimeapp.util.Screens

const val TAG = "AppScreen" +
    ""

@Composable
fun AppScreenContent(
    catViewModel: CategoryViewModel = viewModel(),
    animeListViewModel: AnimeListViewModel = viewModel(),
    paddingValues: PaddingValues,
    controller: NavHostController = rememberNavController(),
    shouldGoHome: Boolean,
) {
    val state by catViewModel.categoryState.collectAsState()
    val listState by animeListViewModel.animeListState.collectAsState()

    NavHost(
        navController = controller,
        startDestination = Screens.CATEGORY.route,
        modifier = Modifier.padding(paddingValues),
    ) {
        composable(Screens.CATEGORY.route) {
            CategoryScreen(categories = state.categoryList) {
                animeListViewModel.getSelectedCategoryList(
                    it.animeLink ?: DEFAULT_ANIME_STRING,
                )
                controller.navigate(Screens.LIST.route)
            }
        }
        composable(Screens.LIST.route) {
            AnimeListScreen(listState.currentList) { selectedData ->
                // Move to next screen with data
                Log.e(
                    TAG,
                    "AppScreen: Moving on to selected screen with ${selectedData.attributes.canonicalTitle}",
                )
                animeListViewModel.setCurrentAnime(selectedData)
                controller.navigate(Screens.DETAILS.route)
            }
        }
        composable(Screens.DETAILS.route) {
            DetailsScreen(listState.currentListItem)
        }
    }

    if (shouldGoHome) {
        controller.navigate(Screens.CATEGORY.route)
    }
}

@Composable
fun DetailsScreen(currentListItem: AnimeResponseData?) {
    currentListItem?.let { item ->
        DetailsScreenContent(item) {
            Log.e(TAG, "DetailsScreen: selected $it")
        }
    } ?: Text(text = "No item is currently selected.")
}

@Composable
fun DetailsScreenContent(
    item: AnimeResponseData,
    onFavoriteSelected: (AnimeResponseData) -> Unit,
) {
    val scrollState = rememberScrollState()
    Card(
        shape = RectangleShape,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item.attributes.apply {
                Box(
                    Modifier.fillMaxSize(),
                ) {
                    AsyncImage(
                        model = item.attributes.posterImage.large,
                        contentDescription = "Description of the anime's poster.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                    )

                    Card(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .heightIn(
                                min = 200.dp,
                                max = 400.dp,
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        ),
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            CardTitleDisplay(this@apply)
                            Divider(
                                Modifier
                                    .fillMaxWidth(.5f)
                                    .background(Color.Black),
                            )

                            Text(
                                text = description,
                                Modifier
                                    .fillMaxWidth(.90f)
                                    .verticalScroll(
                                        scrollState,
                                        enabled = true,
                                    )
                                    .padding(top = 16.dp),
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CardTitleDisplay(attributes: AnimeListAttributes) = with(attributes) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        CardTitleText(canonicalTitle, ageRating, ageRatingGuide)
        Spacer(modifier = Modifier.width(24.dp))
        var isSelected by remember { mutableStateOf(false) }
        ToggleableFavoriteIcon(isSelected) {
            isSelected = !isSelected
            Log.e(
                TAG,
                "DetailsScreenContent: is Selected is -> $isSelected",
            )
        }
    }
}

@Composable
private fun CardTitleText(title: String, ageRating: String, ageRatingGuide: String) {
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.widthIn(min = 100.dp, max = 250.dp),
    ) {
        TitleText(text = title)
        SubTitleText(text = "$ageRating($ageRatingGuide)")
    }
}

@Composable
private fun ToggleableFavoriteIcon(
    isSelected: Boolean,
    onFavoriteSelected: () -> Unit,
) {
    val vector = if (isSelected) {
        Icons.Filled.Favorite
    } else {
        Icons.Outlined.Favorite
    }
    Log.e(TAG, "ToggleableFavoriteIcon: drawing...... $vector")
    Image(
        imageVector = vector,
        contentDescription = "Add anime as favorite",
        modifier = Modifier
            .clickable {
                onFavoriteSelected()
            }
            .widthIn(min = 16.dp, max = 24.dp),
        alignment = Center,
    )
}
