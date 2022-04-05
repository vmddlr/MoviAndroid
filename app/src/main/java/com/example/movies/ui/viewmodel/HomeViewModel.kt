package com.example.movies.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.core.util.UrlConstant
import com.example.movies.core.util.Utilities
import com.example.movies.data.model.response.NowPlayEntityResponse
import com.example.movies.data.model.response.PopulateEntityResponse
import com.example.movies.data.repository.ApiRepository
import com.example.movies.data.room.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ApiRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()

    var mldNowPlayViewModel = MutableLiveData<NowPlayEntityResponse>()
    var mldPopulateViewModel = MutableLiveData<PopulateEntityResponse>()

    fun getNowPlayViewModel(context: Context) {
        mldNowPlayViewModel = MutableLiveData()

        viewModelScope.launch {
            isLoading.postValue(true)
            val room = roomRepository.getAllNowPlaySelectRepository()

            if(Utilities.checkForInternet(context)) {
                val entity = repository.apiRepository<NowPlayEntityResponse>(
                    url = UrlConstant.nowPlay,
                    languaje = "es-ES"
                )
                entity.let {
                    mldNowPlayViewModel.postValue(entity!!)
                    roomRepository.insertNowPlayRepository(entity.results!!)
                }
            } else {
                room.let {
                    val entity = NowPlayEntityResponse(
                        results = roomRepository.getAllNowPlaySelectRepository(),
                        page = 1
                    )

                    mldNowPlayViewModel.postValue(entity)
                }
            }

            isLoading.postValue(false)
        }
    }

    fun getPopulateViewModel(context: Context) {
        mldPopulateViewModel = MutableLiveData()

        viewModelScope.launch {
            isLoading.postValue(true)
            val room = roomRepository.getAllPopulateSelectRepository()

            if(Utilities.checkForInternet(context)) {
                val entity = repository.apiRepository<PopulateEntityResponse>(
                    url = UrlConstant.populate,
                    languaje = "es-ES"
                )

                entity.let {
                    mldPopulateViewModel.postValue(entity!!)
                    roomRepository.insertPopulateRepository(entity.results!!)
                }
            } else {
                room.let {
                    val entity = PopulateEntityResponse(
                        results = roomRepository.getAllPopulateSelectRepository(),
                        page = 1
                    )

                    mldPopulateViewModel.postValue(entity)
                }
            }

            isLoading.postValue(false)
        }
    }
}