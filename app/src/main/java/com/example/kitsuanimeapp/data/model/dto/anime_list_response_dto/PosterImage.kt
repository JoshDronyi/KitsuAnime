package com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto

import kotlinx.serialization.Serializable

@Serializable
data class PosterImage(
    val large: String,
    val meta: MetaX
)