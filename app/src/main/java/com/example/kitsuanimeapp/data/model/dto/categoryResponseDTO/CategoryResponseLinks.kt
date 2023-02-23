package com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponseLinks(
    val first: String,
    val last: String,
    val next: String
)