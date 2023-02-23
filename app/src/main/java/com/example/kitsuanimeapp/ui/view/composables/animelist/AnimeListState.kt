package com.example.kitsuanimeapp.ui.view.composables.animelist

import com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto.AnimeResponseData
import com.example.kitsuanimeapp.util.ErrorTypes

data class AnimeListState(
    val isLoading: Boolean = false,
    val currentList: List<AnimeResponseData> = emptyList(),
    val error: ErrorTypes? = null,
    val currentListItem: AnimeResponseData? = null,
)
