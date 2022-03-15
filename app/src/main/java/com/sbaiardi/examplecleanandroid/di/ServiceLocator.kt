package com.sbaiardi.examplecleanandroid.di

import android.content.Context
import com.sbaiardi.data.api.NetworkModule
import com.sbaiardi.data.datasource.PostLocalDataSource
import com.sbaiardi.data.datasource.PostLocalDataSourceImpl
import com.sbaiardi.data.datasource.PostsRemoteDataSourceImpl
import com.sbaiardi.data.datasource.PostsRepositoryImpl
import com.sbaiardi.data.db.PostDatabase
import com.sbaiardi.data.mappers.PostApiResponseMapper
import com.sbaiardi.data.mappers.PostEntityMapper
import kotlinx.coroutines.Dispatchers

object ServiceLocator {
    private var database: PostDatabase? = null
    private val networkModule by lazy {
        NetworkModule()
    }
    private val bookEntityMapper by lazy {
        PostEntityMapper()
    }

    @Volatile
    var postsRepository: PostsRepositoryImpl? = null

    fun providePostRepository(context: Context): PostsRepositoryImpl {
        // useful because this method can be accessed by multiple threads
        synchronized(this) {
            return postsRepository ?: createPostsRepository(context)
        }
    }

    private fun createPostsRepository(context: Context): PostsRepositoryImpl {
        val newRepo =
            PostsRepositoryImpl(
                createPostsLocalDataSource(context),
                PostsRemoteDataSourceImpl(
                    networkModule.createPostApi("https://www.googleapis.com/"),
                    PostApiResponseMapper()
                )
            )
        postsRepository = newRepo
        return newRepo
    }

    private fun createPostsLocalDataSource(context: Context): PostLocalDataSource {
        val database = database ?: createDataBase(context)
        return PostLocalDataSourceImpl(
            database.postDao(),
            Dispatchers.IO,
            bookEntityMapper
        )
    }

    private fun createDataBase(context: Context): PostDatabase {
        val result = PostDatabase.getDatabase(context)
        database = result
        return result
    }
}