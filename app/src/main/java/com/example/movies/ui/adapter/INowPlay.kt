package com.example.movies.ui.adapter

import com.example.movies.data.model.response.NowPlayResultEntityResponse
import com.example.movies.data.model.response.PopulateResultEntityResponse

interface INowPlay {
    fun onNowPlayClickListener(entity: NowPlayResultEntityResponse)
    fun onPopulateClickListener(entity: PopulateResultEntityResponse)
}