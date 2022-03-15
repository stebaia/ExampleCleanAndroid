package com.sbaiardi.data.datasource

import com.sbaiardi.domain.common.Result
import com.sbaiardi.domain.entities.Post

interface PostsRemoteDataSource {
    suspend fun getPosts(author: String): Result<List<Post>>
}