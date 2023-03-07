package com.example.kitsuanimeapp.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kitsuanimeapp.data.model.KitsuRepo
import com.example.kitsuanimeapp.data.model.local.KitsuDB
import com.example.kitsuanimeapp.data.model.remote.RetrofitObject
import com.example.kitsuanimeapp.ui.theme.KitsuAnimeAppTheme
import com.example.kitsuanimeapp.ui.view.composables.AppScreenContent
import com.example.kitsuanimeapp.ui.viewmodels.AnimeListViewModel
import com.example.kitsuanimeapp.ui.viewmodels.CategoryViewModel
import com.example.kitsuanimeapp.ui.viewmodels.KitsuViewModelFactory

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    catViewModel: CategoryViewModel,
    animeListVM: AnimeListViewModel,
) {
    val context = LocalContext.current
    var goHomeRoger by remember {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                Toast.makeText(
                    context,
                    "We're floating!!!",
                    Toast.LENGTH_SHORT,
                )
            }) {
                Image(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                tonalElevation = 6.dp,
            ) {
                Text(
                    text = "Home",
                    modifier = Modifier.clickable {
                        goHomeRoger = true
                    },
                )
                Text(
                    text = "This will also take you home.",
                    modifier = Modifier.clickable {
                        goHomeRoger = true
                    },
                )
            }
        },
    ) {
        AppScreenContent(catViewModel, animeListVM, it, shouldGoHome = goHomeRoger)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KitsuAnimeAppTheme {
        Greeting("Android")
    }
}
