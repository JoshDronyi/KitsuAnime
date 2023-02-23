package com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO

import kotlinx.serialization.Serializable

@Serializable
data class CategoryData(
    val attributes: Attributes,
    val id: String,
    val links: Links,
    val relationships: Relationships,
    val type: String
)