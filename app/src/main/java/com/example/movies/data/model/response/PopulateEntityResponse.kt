package com.example.movies.data.model.response

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

data class PopulateEntityResponse(
    val page: Long,
    val results: List<PopulateResultEntityResponse>? = null
) : Serializable

@Entity(tableName = "Populate", indices = [Index(value = ["id"], unique = true)])
data class PopulateResultEntityResponse(
    @PrimaryKey(autoGenerate = true) var idPopulate: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val id: Long,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Long
) : Serializable
