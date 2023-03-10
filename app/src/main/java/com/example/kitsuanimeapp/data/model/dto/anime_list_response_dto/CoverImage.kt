package com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto

import kotlinx.serialization.Serializable

@Serializable
data class CoverImage(
    val large: String,
    val original: String,
    val small: String,
    val tiny: String,
)
