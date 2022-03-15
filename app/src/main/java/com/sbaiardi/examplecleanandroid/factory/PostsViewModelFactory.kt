package com.sbaiardi.examplecleanandroid.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sbaiardi.domain.usecases.CheckUseCase
import com.sbaiardi.domain.usecases.GetCheckedUseCase
import com.sbaiardi.domain.usecases.GetPostsUseCase
import com.sbaiardi.domain.usecases.UncheckUseCase
import com.sbaiardi.examplecleanandroid.mappers.PostWithStatusMapper
import com.sbaiardi.examplecleanandroid.viewmodel.PostsViewModel

class PostsViewModelFactory(
    private val checkUseCase: CheckUseCase,
    private val getCheckedUseCase: GetCheckedUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val uncheckUseCase: UncheckUseCase,
    private val mapper: PostWithStatusMapper
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostsViewModel(
            checkUseCase,
            getCheckedUseCase,
            getPostsUseCase,
            uncheckUseCase,
            mapper
        ) as T
    }
}