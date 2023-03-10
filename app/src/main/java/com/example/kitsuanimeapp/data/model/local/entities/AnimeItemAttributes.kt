package com.example.kitsuanimeapp.data.model.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto.CoverImage
import com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto.PosterImage

@Entity
data class AnimeItemAttributes(
    val ageRating: String,
    val ageRatingGuide: String,
    val averageRating: String,
    @PrimaryKey
    val canonicalTitle: String,
    @Embedded("coverImage") val coverImage: CoverImage? = null,
    val coverImageTopOffset: Int,
    val description: String,
    val endDate: String,
    val episodeCount: Int,
    val episodeLength: Int? = null,
    val nsfw: Boolean,
    @Embedded("posterImage") val posterImage: PosterImage,
    val showType: String,
    val slug: String,
    val startDate: String,
    val status: String,
    val subtype: String,
    val synopsis: String,
    val totalLength: Int,
)
