package com.example.movies.data.model.response

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NowPlayEntityResponse(
    var dates: DateEntityResponse? = null,
    var page: Int,
    var results: List<NowPlayResultEntityResponse>? = null
) : Serializable

data class DateEntityResponse(
    var maximum: String,
    var minimum: String
) : Serializable

@Entity(tableName = "NowPlay", indices = [Index(value = ["id"], unique = true)])
data class NowPlayResultEntityResponse(
    @PrimaryKey(autoGenerate = true) var idNowPlay: Int,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdrop_path: String,
    @SerializedName("id") val id: Long,
    @SerializedName("original_title") val original_title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("vote_count") val vote_count: Long
) : Serializable
