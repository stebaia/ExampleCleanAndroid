package com.sbaiardi.data.datasource


import com.sbaiardi.data.db.PostDao
import com.sbaiardi.data.mappers.PostEntityMapper
import com.sbaiardi.domain.entities.Post
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PostLocalDataSourceImpl(
    private val postDao: PostDao,
    private val dispatcher: CoroutineDispatcher,
    private val postEntityMapper: PostEntityMapper
) : PostLocalDataSource {

    override suspend fun check(post: Post) = withContext(dispatcher) {
        postDao.savePost(postEntityMapper.toPostEntity(post))
    }

    override suspend fun uncheck(post: Post) = withContext(dispatcher) {
        postDao.deletePost(postEntityMapper.toPostEntity(post))
    }

    override suspend fun getChecked(): Flow<List<Post>> {
        val savedPostsFlow = postDao.getSavedPosts()
        return savedPostsFlow.map { list ->
            list.map { element ->
                postEntityMapper.toPost(element)
            }
        }
    }
}