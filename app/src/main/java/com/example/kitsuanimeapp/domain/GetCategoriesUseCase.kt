package com.example.kitsuanimeapp.domain

import android.util.Log
import com.example.kitsuanimeapp.data.model.KitsuRepo
import com.example.kitsuanimeapp.data.model.mappers.CategoryDataMapper
import com.example.kitsuanimeapp.data.model.remote.ResponseState
import com.example.kitsuanimeapp.util.ErrorTypes
import com.example.kitsuanimeapp.util.StatusCodes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class GetCategoriesUseCase(
    val repo: KitsuRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val mapper: CategoryDataMapper = CategoryDataMapper(),
) {
    private val TAG = "GetCategoriesUseCase"
    private val theContext: CoroutineContext =
        dispatcher + Job() + CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, "oh nooooo something went wrong!!: ${throwable.localizedMessage}")
        }
    val currentCategies = repo.localCategories
    suspend operator fun invoke(): ResponseState =
        withContext(theContext) {
            val currentList = currentCategies.toList().first()
            var resultingState: ResponseState? = null
            resultingState = if (currentList.isEmpty()) {
                getFromNetwork()
            } else {
                Log.e(TAG, "invoke: Local data. Current List -> $currentList")
                ResponseState.Success.LocalCategories(currentList)
            }
            resultingState
        }

    private suspend fun getFromNetwork() = try {
        val categories = repo.getNetworkCategories().map {
            val newCat = mapper(it)
            repo.saveCategory(newCat)
            newCat
        }
        ResponseState.Success.NetworkCategorySuccess(categories)
    } catch (thrown: Throwable) {
        Log.e(TAG, "getAnimeCategories: the error was ${thrown.localizedMessage}")
        ResponseState.ErrorResponse(
            ErrorTypes.UNKNOWN,
            StatusCodes.SC_400,
            thrown,
        )
    }
}
