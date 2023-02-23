package com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto

import com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO.Links
import kotlinx.serialization.Serializable

@Serializable
data class AnimeResponseData(
    val attributes: AnimeListAttributes,
    val id: String,
    val links: Links,
    val relationships: AnimeListRelationships,
    val type: String
)