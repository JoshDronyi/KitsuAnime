package com.example.kitsuanimeapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kitsuanimeapp.data.model.KitsuRepo
import com.example.kitsuanimeapp.domain.GetCategoriesUseCase

class KitsuViewModelFactory(
    private val repo: KitsuRepo,
    private val getCategoriesUseCase: GetCategoriesUseCase = GetCategoriesUseCase(repo),
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CategoryViewModel::class.java) -> CategoryViewModel(
                getCategoriesUseCase,
            ) as T
            modelClass.isAssignableFrom(AnimeListViewModel::class.java) -> AnimeListViewModel(repo) as T
            else -> {
                throw IllegalArgumentException("Don't know of  a class called ${modelClass.simpleName}")
            }
        }
    }
}
