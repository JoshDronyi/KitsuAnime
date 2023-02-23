package com.example.kitsuanimeapp.data.model.remote

import com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto.AnimeListResponse
import com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO.CategoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface KitsuService {
    @GET(CATEGORIES_ENDPOINT)
    suspend fun getKitsuCategories(): CategoryResponse

    @GET("$CATEGORIES_ENDPOINT{catLink}$ANIME_LIST_ENDPOINT")
    suspend fun getAnimeInCategory(@Path("catLink") catLink: Int): AnimeListResponse

    companion object {
        const val CATEGORIES_ENDPOINT = "categories/"
        const val ANIME_LIST_ENDPOINT = "/anime"
    }
}
