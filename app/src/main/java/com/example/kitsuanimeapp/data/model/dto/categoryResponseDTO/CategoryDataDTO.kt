package com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDataDTO(
    val attributes: AttributesDTO? = null,
    val id: String? = null,
    val links: Links? = null,
    val relationships: Relationships? = null,
    val type: String,
) {
    companion object {
        fun createBlankObject(): CategoryDataDTO {
            return CategoryDataDTO(type = "Toaster Strudel")
        }
    }
}
