package com.example.kitsuanimeapp.data.model.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @Embedded
    val attributes: Attributes? = null,
    val selfLink: String? = null,
    val animeLink: String? = null,
    val type: String,
)
