package com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto

import com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO.Meta
import kotlinx.serialization.Serializable

@Serializable
data class CoverImage(
    val large: String,
    val meta: Meta,
    val original: String,
    val small: String,
    val tiny: String
)