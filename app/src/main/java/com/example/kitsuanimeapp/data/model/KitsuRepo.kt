package com.example.kitsuanimeapp.data.model

import android.util.Log
import com.example.kitsuanimeapp.data.model.dto.anime_list_response_dto.AnimeResponseData
import com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO.CategoryDataDTO
import com.example.kitsuanimeapp.data.model.local.dao.KitsuDao
import com.example.kitsuanimeapp.data.model.local.entities.CategoryData
import com.example.kitsuanimeapp.data.model.mappers.AnimeMapper
import com.example.kitsuanimeapp.data.model.remote.KitsuService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

open class KitsuRepo private constructor(
    private val service: KitsuService,
    private val dao: KitsuDao,
    private val animeMapper: AnimeMapper = AnimeMapper(),
) {
    private val TAG = "KitsuRepo"

    val localCategories = channelFlow {
        dao.getAllCategories().collectLatest {
            send(it)
        }
    }

    suspend fun getNetworkCategories(): List<CategoryDataDTO> = withContext(Dispatchers.IO) {
        try {
            service.getKitsuCategories().categoryData
        } catch (thrown: Throwable) {
            emptyList<CategoryDataDTO>()
        }
    }

    suspend fun saveCategory(categoryData: CategoryData) {
        dao.addCategory(categoryData)
    }

    suspend fun getAnimeFromCategory(catLink: String) = withContext(Dispatchers.IO) {
        val index = catLink.getCatIdIndex()
        Log.e(TAG, "getAnimeFromCategory: $index")
        val result = service.getAnimeInCategory(index.digitToInt())
        Log.e(TAG, "getAnimeFromCategory:result was $result")
        result.animeList
    }

    fun saveFavoriteAnime(data: AnimeResponseData) {
        dao.saveAnime(
            animeMapper(data.attributes),
        )
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
