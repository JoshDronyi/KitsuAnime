package com.example.kitsuanimeapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitsuanimeapp.data.model.KitsuRepo
import com.example.kitsuanimeapp.data.model.remote.ResponseState
import com.example.kitsuanimeapp.ui.view.composables.category.CategoryState
import com.example.kitsuanimeapp.util.ErrorTypes
import com.example.kitsuanimeapp.util.StatusCodes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repo: KitsuRepo,
) : ViewModel() {
    val TAG = "CategoryViewModel"

    private val _categoryState: MutableStateFlow<CategoryState> = MutableStateFlow(CategoryState())
    val categoryState: StateFlow<CategoryState> get() = _categoryState

    fun getAnimeCategories(dispatcher: CoroutineDispatcher = Dispatchers.IO) =
        viewModelScope.launch(dispatcher) {
            _categoryState.update {
                it.copy(isLoading = true)
            }
            try {
                val categories = repo.getCategories()
                _categoryState.update {
                    it.copy(categoryList = categories)
                }
            } catch (thrown: Throwable) {
                Log.e(TAG, "getAnimeCategories: the error was ${thrown.localizedMessage}")
                _categoryState.update {
                    it.copy(
                        error = ResponseState.ErrorResponse(
                            ErrorTypes.UNKNOWN,
                            StatusCodes.SC_400,
                            thrown,
                        ),
                    )
                }
            } finally {
                _categoryState.update {
                    it.copy(isLoading = false)
                }
            }
        }
}
