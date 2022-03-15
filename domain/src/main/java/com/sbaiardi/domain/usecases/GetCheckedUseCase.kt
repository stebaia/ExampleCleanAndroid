package com.sbaiardi.domain.usecases

import com.sbaiardi.domain.repositories.PostDomainRepository

class GetCheckedUseCase(private val repository: PostDomainRepository) {
    suspend operator fun invoke() = repository.getChecked()
}