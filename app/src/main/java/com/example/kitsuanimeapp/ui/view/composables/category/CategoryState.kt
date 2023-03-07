package com.example.kitsuanimeapp.ui.view.composables.category

import com.example.kitsuanimeapp.data.model.local.entities.CategoryData
import com.example.kitsuanimeapp.data.model.remote.ResponseState

data class CategoryState(
    val isLoading: Boolean = false,
    val error: ResponseState.ErrorResponse? = null,
    val categoryList: List<CategoryData> = emptyList(),
)
