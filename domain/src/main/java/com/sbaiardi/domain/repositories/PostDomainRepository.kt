package com.sbaiardi.domain.repositories

import com.sbaiardi.domain.common.Result
import com.sbaiardi.domain.entities.Post
import kotlinx.coroutines.flow.Flow

interface PostDomainRepository {

    suspend fun getRemotePosts(author: String): Result<List<Post>>

    suspend fun getChecked(): Flow<List<Post>>

    suspend fun check(post: Post)

    suspend fun uncheck(post: Post)

}