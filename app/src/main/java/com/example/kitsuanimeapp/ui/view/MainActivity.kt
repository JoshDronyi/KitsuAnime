package com.example.kitsuanimeapp.ui.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kitsuanimeapp.data.model.KitsuRepo
import com.example.kitsuanimeapp.data.model.local.KitsuDB
import com.example.kitsuanimeapp.data.model.remote.RetrofitObject
import com.example.kitsuanimeapp.ui.theme.KitsuAnimeAppTheme
import com.example.kitsuanimeapp.ui.view.composables.DetailsScreen
import com.example.kitsuanimeapp.ui.view.composables.TAG
import com.example.kitsuanimeapp.ui.view.composables.animelist.AnimeListScreen
import com.example.kitsuanimeapp.ui.view.composables.category.CategoryScreen
import com.example.kitsuanimeapp.ui.viewmodels.AnimeListViewModel
import com.example.kitsuanimeapp.ui.viewmodels.CategoryViewModel
import com.example.kitsuanimeapp.ui.viewmodels.KitsuViewModelFactory
import com.example.kitsuanimeapp.util.DEFAULT_ANIME_STRING
import com.example.kitsuanimeapp.util.Screens
import com.example.kitsuanimeapp.util.screen_list

class MainActivity : ComponentActivity() {

    private val repo by lazy {
        val db = KitsuDB.getInstance(this)
        KitsuRepo.getInstance(
            RetrofitObject.getKitsuService(),
            db.getKitsuDao(),
        )
    }
    private val catViewModel by viewModels<CategoryViewModel> {
        KitsuViewModelFactory(repo)
    }
    private val animeListVM by viewModels<AnimeListViewModel> {
        KitsuViewModelFactory(repo)
    }
    var preferenceGotFromDynamicSource: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catViewModel.getAnimeCategories()

        setContent {
            KitsuAnimeAppTheme(dynamicColor = preferenceGotFromDynamicSource) {
                // A surface container using the 'background' color from the theme
                AppScreen(
                    catViewModel,
                    animeListVM,
                )
            }
        }
    }
}

@Composable
fun AppScreen(
    catViewModel: CategoryViewModel,
    animeListVM: AnimeListViewModel,
) {
    val context = LocalContext.current
    val controller = rememberNavController()

    val state by catViewModel.categoryState.collectAsState()
    val listState by animeListVM.animeListState.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                Toast.makeText(
                    context,
                    "We're floating!!!",
                    Toast.LENGTH_SHORT,
                ).show()
            }) {
                Image(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                tonalElevation = 6.dp,
            ) {
                var selected by remember { mutableStateOf(false) }
                screen_list.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = context.getString(screen.stringResource),
                            )
                        },
                        label = {
                            Text(text = screen.name)
                        },
                        selected = false,
                        onClick = {
                            selected = true
                            controller.navigate(screen.route) {
                                launchSingleTop = true
                            }
                        },
                    )
                }
            }
        },
    ) { values ->
        NavHost(
            navController = controller,
            startDestination = Screens.CATEGORY.route,
            modifier = Modifier.padding(values),
        ) {
            composable(Screens.CATEGORY.route) {
                CategoryScreen(categories = state.categoryList) {
                    animeListVM.getSelectedCategoryList(
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
                    animeListVM.setCurrentAnime(selectedData)
                    controller.navigate(Screens.DETAILS.route)
                }
            }
            composable(Screens.DETAILS.route) {
                DetailsScreen(listState.currentListItem) {
                    animeListVM.saveCurrentAnime(it)
                }
            }
        }
    }
}
