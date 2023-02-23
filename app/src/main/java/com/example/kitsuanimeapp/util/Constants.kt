package com.example.kitsuanimeapp.util

enum class ErrorTypes {
    INVALID_REQUEST,
    INVALID_CLIENT,
    INVALID_GRANT,
    INVALID_SCOPE,
    UNAUTHORIZED_CLIENT,
    UNSUPPORTED_GRANT_TYPE,
    UNKNOWN,
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

enum class Screens(val route: String) {
    CATEGORY("category"),
    LIST("list"),
    DETAILS("details"),
}
