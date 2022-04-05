package com.example.movies.data.model.response

data class VideoEntityResponse(
    val id: Long,
    val results: List<Result>
)


data class Result(
    val name: String,
    val key: String,
    val site: String,
    val size: Long,
    val type: String,
    val official: Boolean,
    val publishedAt: String,
    val id: String
)