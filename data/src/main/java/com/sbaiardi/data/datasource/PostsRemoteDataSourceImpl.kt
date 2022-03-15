package com.sbaiardi.data.datasource

import com.sbaiardi.domain.common.Result
import com.sbaiardi.data.api.PostApi
import com.sbaiardi.data.mappers.PostApiResponseMapper
import com.sbaiardi.domain.entities.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostsRemoteDataSourceImpl(
    private val service: PostApi,
    private val mapper: PostApiResponseMapper
) : PostsRemoteDataSource {

    override suspend fun getPosts(author: String): Result<List<Post>> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getPosts(author)
                if (response.isSuccessful) {
                    return@withContext Result.Success(mapper.toPostList(response.body()!!))
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
}