package com.example.movies.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.data.model.response.NowPlayResultEntityResponse
import com.example.movies.data.model.response.PopulateResultEntityResponse

@Database(
    entities = [NowPlayResultEntityResponse::class, PopulateResultEntityResponse::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun appDao(): AppDao
}