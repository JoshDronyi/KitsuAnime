package com.example.kitsuanimeapp.ui.view.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
                    it.relationships?.anime?.links?.related ?: DEFAULT_ANIME_STRING,
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
        DetailsScreenContent(item)
    } ?: Text(text = "No item is currently selected.")
}

@Composable
fun DetailsScreenContent(item: AnimeResponseData) {
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
                            containerColor = Color.White,
                        ),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            TitleText(text = canonicalTitle)
                            SubTitleText(text = "$ageRating($ageRatingGuide)")
                            Divider(
                                Modifier.fillMaxWidth(.5f)
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
