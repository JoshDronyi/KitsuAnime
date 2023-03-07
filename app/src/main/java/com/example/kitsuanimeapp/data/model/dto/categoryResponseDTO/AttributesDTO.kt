package com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO

import kotlinx.serialization.Serializable

@Serializable
data class AttributesDTO(
    val childCount: Int,
    val createdAt: String,
    val description: String,
    val nsfw: Boolean,
    val slug: String,
    val title: String,
    val totalMediaCount: Int,
    val updatedAt: String
)