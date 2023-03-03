package com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO

import kotlinx.serialization.Serializable

@Serializable
data class CategoryData(
    val attributes: Attributes? = null,
    val id: String? = null,
    val links: Links? = null,
    val relationships: Relationships? = null,
    val type: String,
) {
    companion object {
        fun createBlankObject(): CategoryData {
            return CategoryData(type = "Toaster Strudel")
        }
    }
}
