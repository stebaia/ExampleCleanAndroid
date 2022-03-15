package com.sbaiardi.data.datasource

import com.sbaiardi.domain.common.Result
import com.sbaiardi.domain.entities.Post
import com.sbaiardi.domain.repositories.PostDomainRepository
import kotlinx.coroutines.flow.Flow

class PostsRepositoryImpl(
    private val localDataSource: PostLocalDataSource,
    private val remoteDataSource: PostsRemoteDataSource
) : PostDomainRepository {

    override suspend fun getRemotePosts(author: String): Result<List<Post>> {
        return remoteDataSource.getPosts(author)
    }

    override suspend fun getChecked(): Flow<List<Post>> {
        return localDataSource.getChecked()
    }

    override suspend fun check(post: Post) {
        localDataSource.check(post)
    }

    override suspend fun uncheck(post: Post) {
        localDataSource.uncheck(post)
    }
}