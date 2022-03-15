package com.sbaiardi.data.db

import androidx.room.*
import com.sbaiardi.data.entities.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePost(post: PostEntity)

    @Query("SELECT * FROM post")
    fun getSavedPosts(): Flow<List<PostEntity>>

    @Delete
    suspend fun deletePost(post: PostEntity)

}