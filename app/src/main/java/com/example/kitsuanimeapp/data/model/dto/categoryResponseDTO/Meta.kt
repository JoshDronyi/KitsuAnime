package com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    @SerialName("count")
    val count: Int? = null
)
