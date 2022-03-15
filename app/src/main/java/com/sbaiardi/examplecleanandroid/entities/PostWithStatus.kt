package com.sbaiardi.examplecleanandroid.entities

data class PostWithStatus(
    val id: String,
    val title: String,
    val authors: List<String>,
    val imageUrl: String?,
    val status: PostCheckingStatusEnum
)