package com.sbaiardi.data.mappers

import com.sbaiardi.data.entities.PostEntity
import com.sbaiardi.domain.entities.Post
import com.sbaiardi.domain.entities.PostInfo

class PostEntityMapper {

    fun toPostEntity(post: Post): PostEntity {
        return PostEntity(
            id = post.id,
            title = post.postInfo.title,
            authors = post.postInfo.authors,
            imageUrl = post.postInfo.imageUrl
        )
    }

    fun toPost(postEntity: PostEntity): Post {
        return Post(
            postEntity.id,
            PostInfo(postEntity.title, postEntity.authors, postEntity.imageUrl)
        )
    }

}