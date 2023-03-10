package com.example.kitsuanimeapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitsuanimeapp.data.model.KitsuRepo
import com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto.AnimeResponseData
import com.example.kitsuanimeapp.ui.view.composables.animelist.AnimeListState
import com.example.kitsuanimeapp.util.ErrorTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnimeListViewModel(private val repo: KitsuRepo) : ViewModel() {
    private val _animeList: MutableStateFlow<AnimeListState> = MutableStateFlow(AnimeListState())
    val animeListState: StateFlow<AnimeListState> get() = _animeList

    fun getSelectedCategoryList(catLink: String) = viewModelScope.launch(Dispatchers.IO) {
        _animeList.update {
            it.copy(isLoading = true)
        }
        val dataList = repo.getAnimeFromCategory(catLink)
        _animeList.update {
            it.copy(isLoading = false, currentList = dataList)
        }
    }

    fun setCurrentAnime(selectedData: AnimeResponseData) {
        _animeList.update {
            it.copy(currentListItem = selectedData)
        }
    }

    fun saveCurrentAnime(data: AnimeResponseData) {
        _animeList.update {
            it.copy(isLoading = true, currentListItem = data)
        }
        try {
            repo.saveFavoriteAnime(data)
        } catch (thrown: Throwable) {
            _animeList.update {
                it.copy(isLoading = false, error = ErrorTypes.UNKNOWN)
            }
        }
    }
}
