@file:OptIn(ExperimentalSerializationApi::class)

package com.example.kitsuanimeapp.data.model.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create

object RetrofitObject {
    private val json = Json {
        explicitNulls = true
        ignoreUnknownKeys = true
    }
    private val mediaType = "application/json".toMediaType()

    private const val BASE_URL = "https://kitsu.io/api/edge/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory(mediaType))
        .baseUrl(BASE_URL)
        .build()

    fun getKitsuService(): KitsuService = retrofit.create()
}
