package com.example.kitsuanimeapp.data.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kitsuanimeapp.data.model.local.entities.AnimeItemAttributes
import com.example.kitsuanimeapp.data.model.local.entities.CategoryData
import kotlinx.coroutines.flow.Flow

@Dao
interface KitsuDao {

    @Query("SELECT * FROM CategoryData")
    fun getAllCategories(): Flow<List<CategoryData>>

    @Insert(entity = CategoryData::class, onConflict = OnConflictStrategy.IGNORE)
    fun addCategory(vararg categoryData: CategoryData)

    @Insert(entity = AnimeItemAttributes::class, onConflict = OnConflictStrategy.ABORT)
    fun saveAnime(vararg data: AnimeItemAttributes)
}
