package com.sbaiardi.examplecleanandroid.mappers

import com.sbaiardi.domain.entities.Post
import com.sbaiardi.domain.entities.PostInfo
import com.sbaiardi.examplecleanandroid.entities.PostCheckingStatusEnum
import com.sbaiardi.examplecleanandroid.entities.PostWithStatus

class PostWithStatusMapper {

    fun fromPostDomainToPostWithStatus(
        posts: List<Post>,
        _posts: List<Post>
    ): List<PostWithStatus> {
        val commonElements = posts.filter { it.id in _posts.map { post -> post.id } }
        val postsWithStatus = arrayListOf<PostWithStatus>()
        for (post in posts) {
            if (post in commonElements) {
                postsWithStatus.add(
                    PostWithStatus(
                        post.id,
                        post.postInfo.title,
                        post.postInfo.authors,
                        post.postInfo.imageUrl,
                        PostCheckingStatusEnum.CHECKED
                    )
                )
            } else {
                postsWithStatus.add(
                    PostWithStatus(
                        post.id,
                        post.postInfo.title,
                        post.postInfo.authors,
                        post.postInfo.imageUrl,
                        PostCheckingStatusEnum.UNCHECKED
                    )
                )
            }
        }
        return postsWithStatus.sortedBy { it.id }
    }

    fun fromPostWithStatusToPost(postWithStatus: PostWithStatus): Post {
        return Post(
            postWithStatus.id,
            PostInfo(postWithStatus.title, postWithStatus.authors, postWithStatus.imageUrl)
        )
    }

}