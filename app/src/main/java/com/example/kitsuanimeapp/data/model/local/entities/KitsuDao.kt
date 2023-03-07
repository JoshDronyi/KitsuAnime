package com.example.kitsuanimeapp.data.model.local.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface KitsuDao {

    @Query("SELECT * FROM CategoryData")
    fun getAllCategories(): List<CategoryData>

    @Insert(entity = CategoryData::class)
    fun addCategory(vararg categoryData: CategoryData)
}
