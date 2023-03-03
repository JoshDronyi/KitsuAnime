package com.example.kitsuanimeapp.data.model

import android.util.Log
import com.example.kitsuanimeapp.data.model.remote.KitsuService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class KitsuRepo private constructor(
    private val service: KitsuService,
) {
    private val TAG = "KitsuRepo"
    suspend fun getCategories() = withContext(Dispatchers.IO) {
        service.getKitsuCategories().categoryData
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
        fun getInstance(service: KitsuService): KitsuRepo {
            return instance ?: KitsuRepo(service)
        }
    }
}

fun String.getCatIdIndex(): Char {
    val animeIndex = this.indexOf("anime")
    return this[animeIndex - 2]
}
