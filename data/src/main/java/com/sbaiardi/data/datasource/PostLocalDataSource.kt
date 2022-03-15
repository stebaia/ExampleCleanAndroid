package com.sbaiardi.data.datasource

import com.sbaiardi.domain.entities.Post
import kotlinx.coroutines.flow.Flow

interface PostLocalDataSource {
    suspend fun check(post: Post)
    suspend fun uncheck(post: Post)
    suspend fun getChecked(): Flow<List<Post>>
}