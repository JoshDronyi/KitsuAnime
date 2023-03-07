package com.example.kitsuanimeapp.data.model

import android.util.Log
import com.example.kitsuanimeapp.data.model.local.entities.CategoryData
import com.example.kitsuanimeapp.data.model.local.entities.KitsuDao
import com.example.kitsuanimeapp.data.model.mappers.CategoryDataMapper
import com.example.kitsuanimeapp.data.model.remote.KitsuService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class KitsuRepo private constructor(
    private val service: KitsuService,
    private val dao: KitsuDao,
    private val mapper: CategoryDataMapper = CategoryDataMapper(),
) {
    private val TAG = "KitsuRepo"
    suspend fun getCategories(): List<CategoryData> = withContext(Dispatchers.IO) {
        return@withContext dao.getAllCategories().ifEmpty {
            try {
                service.getKitsuCategories().categoryData
                    .map {
                        val convertedItem = mapper(it)
                        dao.addCategory(convertedItem)
                        convertedItem
                    }
            } catch (thrown: Throwable) {
                emptyList<CategoryData>()
            }
        }
    }

    suspend fun getAnimeFromCategory(catLink: String) = withContext(Dispatchers.IO) {
        val index = catLink.getCatIdIndex()
        Log.e(TAG, "getAnimeFromCategory: $index")
        val result = service.getAnimeInCategory(index.digitToInt())
        Log.e(TAG, "getAnimeFromCategory:result was $result")
        result.animeList
    }

    companion object {
        private var instance: KitsuRepo? = null
        fun getInstance(service: KitsuService, dao: KitsuDao): KitsuRepo {
            return instance ?: KitsuRepo(service, dao)
        }
    }
}

fun String.getCatIdIndex(): Char {
    Log.e("TAG", "getCatIdIndex: $this")
    val animeIndex = this.indexOf("relationships")
    return this[animeIndex - 2]
}
