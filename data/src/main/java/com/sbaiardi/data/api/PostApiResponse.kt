package com.sbaiardi.data.api

import com.squareup.moshi.Json

class PostApiResponse(val items: List<Item>)

data class Item(
    @field:Json(name = "id")
    val id : String,
    @field:Json(name = "volumeInfo")
    val volumeInfo: ApiPostInfo
)

data class ApiPostInfo(
    val title: String,
    val authors: List<String>,
    val imageLinks: ImageLinks?
)

data class ImageLinks(val smallThumbnail: String, val thumbnail: String)