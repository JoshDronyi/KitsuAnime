package com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO

import kotlinx.serialization.Serializable

@Serializable
data class Links(
    val self: String,
    val related: String? = null
)