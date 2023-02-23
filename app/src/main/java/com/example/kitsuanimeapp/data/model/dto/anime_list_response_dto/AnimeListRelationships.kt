package com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto

import kotlinx.serialization.Serializable

@Serializable
data class AnimeListRelationships(
    val animeCharacters: LinksObject,
    val animeProductions: LinksObject,
    val animeStaff: LinksObject,
    val castings: LinksObject,
    val categories: LinksObject,
    val characters: LinksObject,
    val episodes: LinksObject,
    val genres: LinksObject,
    val installments: LinksObject,
    val mappings: LinksObject,
    val mediaRelationships: LinksObject,
    val productions: LinksObject,
    val quotes: LinksObject,
    val reviews: LinksObject,
    val staff: LinksObject,
    val streamingLinks: LinksObject
)
