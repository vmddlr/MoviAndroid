package com.example.movies.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.model.response.NowPlayResultEntityResponse
import com.example.movies.data.model.response.PopulateResultEntityResponse

@Dao
interface AppDao {

    @Query("SELECT * FROM NowPlay")
    suspend fun getAllNowPlaySelect(): List<NowPlayResultEntityResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNowPlay(entity: List<NowPlayResultEntityResponse>)

    @Query("SELECT * FROM Populate")
    suspend fun getAllPopulateSelect(): List<PopulateResultEntityResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopulate(entity: List<PopulateResultEntityResponse>)
}