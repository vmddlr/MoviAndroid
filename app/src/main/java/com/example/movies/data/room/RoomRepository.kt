package com.example.movies.data.room

import com.example.movies.data.model.response.NowPlayResultEntityResponse
import com.example.movies.data.model.response.PopulateResultEntityResponse
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val appDao: AppDao
) {
    suspend fun getAllNowPlaySelectRepository(): List<NowPlayResultEntityResponse> {
        return this.appDao.getAllNowPlaySelect()
    }

    suspend fun insertNowPlayRepository(list: List<NowPlayResultEntityResponse>) {
        this.appDao.insertNowPlay(list)
    }

    suspend fun getAllPopulateSelectRepository(): List<PopulateResultEntityResponse> {
        return this.appDao.getAllPopulateSelect()
    }

    suspend fun insertPopulateRepository(list: List<PopulateResultEntityResponse>) {
        this.appDao.insertPopulate(list)
    }
}