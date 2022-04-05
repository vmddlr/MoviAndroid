package com.example.movies.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.core.util.UrlConstant
import com.example.movies.data.model.response.VideoEntityResponse
import com.example.movies.data.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()

    var mldViewModel = MutableLiveData<VideoEntityResponse>()

    fun getVideoViewModel(movie_id: String) {
        mldViewModel = MutableLiveData()

        viewModelScope.launch {
            isLoading.postValue(true)

            mldViewModel.postValue(
                repository.apiRepository(
                    url = UrlConstant.video.replace("movie_id", movie_id),
                    languaje = "en-US"
                )
            )
            isLoading.postValue(false)
        }
    }
}