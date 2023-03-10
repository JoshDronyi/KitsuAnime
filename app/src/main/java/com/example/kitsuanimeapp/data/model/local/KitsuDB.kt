package com.example.kitsuanimeapp.data.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kitsuanimeapp.data.model.local.dao.KitsuDao
import com.example.kitsuanimeapp.data.model.local.entities.AnimeItemAttributes
import com.example.kitsuanimeapp.data.model.local.entities.CategoryData

@Database(
    entities = [CategoryData::class, AnimeItemAttributes::class],
    version = 1,
    exportSchema = true,
)
abstract class KitsuDB : RoomDatabase() {

    abstract fun getKitsuDao(): KitsuDao

    companion object {
        private const val DB_NAME = "KITSU_DB"
        private var instance: KitsuDB? = null
        fun getInstance(context: Context): KitsuDB {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    KitsuDB::class.java,
                    DB_NAME,
                ).build()
            }
        }
    }
}
