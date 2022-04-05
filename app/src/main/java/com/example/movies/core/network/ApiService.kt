package com.example.movies.core.network

import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("{url}")
    suspend fun getServiceCoroutine(
        @Path(value = "url", encoded = true) url: String,
        @Query("language") language: String
    ): Response<Any>

    @POST("{url}")
    suspend fun postServiceCoroutine(
        @Path(value = "url", encoded = true) url: String,
        @Body entity: Any
    ): Response<Any>
}