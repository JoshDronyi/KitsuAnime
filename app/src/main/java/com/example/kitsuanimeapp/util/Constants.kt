package com.example.kitsuanimeapp.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kitsuanimeapp.R

enum class ErrorTypes {
    INVALID_REQUEST, INVALID_CLIENT, INVALID_GRANT, INVALID_SCOPE, UNAUTHORIZED_CLIENT, UNSUPPORTED_GRANT_TYPE, UNKNOWN,
}

object StatusCodes {
    const val SC_200 = 200
    const val SC_201 = 201
    const val SC_204 = 204
    const val SC_400 = 400
    const val SC_401 = 402
    const val SC_404 = 404
    const val SC_406 = 406
    const val SC_5XX = 500
}

const val DEFAULT_ANIME_STRING = "0/anime"
const val PAGE_PERCENTAGE = .85f

enum class Screens(val route: String, val stringResource: Int, val icon: ImageVector) {
    CATEGORY("category", R.string.categoryScreen, Icons.Outlined.Home),
    LIST("list", R.string.listScreen, Icons.Outlined.List),
    DETAILS("details", R.string.detailsScreen, Icons.Outlined.Build),
}

val screen_list = listOf(Screens.CATEGORY, Screens.LIST, Screens.DETAILS)
