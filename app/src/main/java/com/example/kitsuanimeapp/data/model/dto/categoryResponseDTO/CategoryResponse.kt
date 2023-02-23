package com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("data")
    val categoryData: List<CategoryData>,
    val links: CategoryResponseLinks,
    val meta: Meta
)