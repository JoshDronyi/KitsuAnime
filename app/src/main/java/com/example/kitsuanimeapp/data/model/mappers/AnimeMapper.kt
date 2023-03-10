package com.example.kitsuanimeapp.data.model.mappers

import com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto.AnimeListAttributesDTO
import com.example.kitsuanimeapp.data.model.local.entities.AnimeItemAttributes

class AnimeMapper : Mapper<AnimeListAttributesDTO, AnimeItemAttributes> {
    override fun invoke(dto: AnimeListAttributesDTO): AnimeItemAttributes = with(dto) {
        return AnimeItemAttributes(
            ageRating,
            ageRatingGuide,
            averageRating,
            canonicalTitle,
            coverImage,
            coverImageTopOffset,
            description,
            endDate,
            episodeCount,
            episodeLength,
            nsfw,
            posterImage,
            showType,
            slug,
            startDate,
            status,
            subtype,
            synopsis,
            totalLength,
        )
    }
}
