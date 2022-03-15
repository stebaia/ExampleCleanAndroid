package com.sbaiardi.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class PostEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val authors: List<String>,
    val imageUrl: String?
) {
}