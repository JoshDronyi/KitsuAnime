package com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO

import kotlinx.serialization.Serializable

@Serializable
data class Relationships(
    val anime: Anime,
    val drama: Drama,
    val manga: Manga,
    val parent: Parent
)