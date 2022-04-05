package com.example.movies.data.repository

import android.util.Log
import com.example.movies.core.network.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(
    val apiService: ApiService
) {

    suspend inline fun <reified T> apiRepository(
        url: String,
        languaje: String,
        entityRequest: Any? = null,
    ): T? {

        return withContext(Dispatchers.IO) {

            try {
                val response = if (entityRequest == null) {
                    apiService.getServiceCoroutine(url, languaje)
                } else {
                    apiService.postServiceCoroutine(url, entityRequest)
                }

                convertEntity<T>(response)
            } catch (t: Throwable) {
                Log.e("Error", t.message.toString())
                null
            }
        }
    }

    inline fun <reified T> convertEntity(response: Response<Any>): T? {
        val jsonObject = if (response.body() != null) {
            JSONObject(Gson().toJson(response.body()))
        } else {
            JSONObject(response.errorBody()!!.charStream().readText())
        }

        return if (response.isSuccessful) {
            Gson().fromJson(jsonObject.toString(), T::class.java)
        } else {
            null
        }
    }
}