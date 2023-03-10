package com.example.kitsuanimeapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitsuanimeapp.data.model.local.entities.CategoryData
import com.example.kitsuanimeapp.data.model.remote.ResponseState
import com.example.kitsuanimeapp.domain.GetCategoriesUseCase
import com.example.kitsuanimeapp.ui.view.composables.category.CategoryState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    val useCase: GetCategoriesUseCase,
) : ViewModel() {
    private val TAG = "CategoryViewModel"

    private val _categoryState: MutableStateFlow<CategoryState> = MutableStateFlow(CategoryState())
    val categoryState: StateFlow<CategoryState> get() = _categoryState
    private var currentCategories: MutableList<CategoryData> = mutableListOf()

    init {
        viewModelScope.launch {
            useCase.currentCategies.collect { newData ->
                _categoryState.update {
                    it.copy(
                        categoryList = newData,
                    )
                }
            }
        }
    }

    fun getAnimeCategories(dispatcher: CoroutineDispatcher = Dispatchers.IO) =
        viewModelScope.launch(dispatcher) {
            _categoryState.update {
                it.copy(isLoading = true)
            }
            if (currentCategories.isEmpty()) {
                getFromNetwork()
            } else {
                _categoryState.update {
                    it.copy(
                        isLoading = false,
                        categoryList = currentCategories,
                    )
                }
            }
        }

    private suspend fun getFromNetwork() {
        when (val state = useCase()) {
            is ResponseState.Success.NetworkCategorySuccess,
            is ResponseState.Success.LocalCategories,
            -> {
                _categoryState.update {
                    it.copy(
                        isLoading = false,
                        categoryList = (state as ResponseState.Success.CategorySuccess).list,
                    )
                }
            }
            is ResponseState.ErrorResponse -> {
                _categoryState.update {
                    it.copy(
                        isLoading = false,
                        error = state,
                    )
                }
            }
            else -> {
                Log.e(TAG, "getAnimeCategories:  Please specify your success.")
            }
        }
    }
}
