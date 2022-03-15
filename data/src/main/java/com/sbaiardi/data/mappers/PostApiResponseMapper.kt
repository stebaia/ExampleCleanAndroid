package com.sbaiardi.data.mappers

import com.sbaiardi.data.api.PostApiResponse
import com.sbaiardi.domain.entities.Post
import com.sbaiardi.domain.entities.PostInfo

class PostApiResponseMapper {
    fun toPostList(response: PostApiResponse): List<Post> {
        return response.items.map {
            Post(
                it.id, PostInfo(
                    it.volumeInfo.title,
                    it.volumeInfo.authors,
                    it.volumeInfo.imageLinks?.thumbnail?.replace("http", "https")
                )
            )
        }
    }
}