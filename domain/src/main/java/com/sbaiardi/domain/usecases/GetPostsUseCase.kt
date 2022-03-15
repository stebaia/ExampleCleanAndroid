package com.sbaiardi.domain.usecases

import com.sbaiardi.domain.repositories.PostDomainRepository

class GetPostsUseCase(private val repository: PostDomainRepository) {
    suspend operator fun invoke(author: String) = repository.getRemotePosts(author)
}