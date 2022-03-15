package com.sbaiardi.domain.usecases

import com.sbaiardi.domain.entities.Post
import com.sbaiardi.domain.repositories.PostDomainRepository

class CheckUseCase(private val repository: PostDomainRepository) {
    suspend operator fun invoke(postDomain: Post) = repository.check(postDomain)
}