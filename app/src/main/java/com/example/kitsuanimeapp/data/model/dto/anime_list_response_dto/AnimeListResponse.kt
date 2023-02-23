package com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeListResponse(
    @SerialName("data")
    val animeList: List<AnimeResponseData>,
    @SerialName("links")
    val links: Pagination,
    val meta: MetaX,
)
