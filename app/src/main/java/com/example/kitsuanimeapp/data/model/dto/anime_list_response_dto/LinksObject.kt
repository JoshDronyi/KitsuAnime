package com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto

import com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO.Links
import kotlinx.serialization.Serializable

@Serializable
data class LinksObject(
    val links: Links
)