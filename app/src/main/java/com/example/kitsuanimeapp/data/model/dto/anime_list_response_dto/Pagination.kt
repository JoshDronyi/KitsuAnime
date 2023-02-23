package com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto

import kotlinx.serialization.Serializable

@Serializable
data class Pagination(
    val first: String,
    val last: String,
    val next: String
)