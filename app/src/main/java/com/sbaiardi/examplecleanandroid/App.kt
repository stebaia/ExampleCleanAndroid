package com.sbaiardi.examplecleanandroid

import android.app.Application
import com.sbaiardi.data.datasource.PostsRepositoryImpl
import com.sbaiardi.domain.usecases.CheckUseCase
import com.sbaiardi.domain.usecases.GetCheckedUseCase
import com.sbaiardi.domain.usecases.GetPostsUseCase
import com.sbaiardi.domain.usecases.UncheckUseCase
import com.sbaiardi.examplecleanandroid.di.ServiceLocator
import com.sbaiardi.examplecleanandroid.mappers.PostWithStatusMapper
import timber.log.Timber

class App : Application() {
    private val postsRepository: PostsRepositoryImpl
        get() = ServiceLocator.providePostRepository(this)

    val getPostsUseCase: GetPostsUseCase
        get() = GetPostsUseCase(postsRepository)

    val getCheckedUseCase: GetCheckedUseCase
        get() = GetCheckedUseCase(postsRepository)

    val checkedUseCase: CheckUseCase
        get() = CheckUseCase(postsRepository)

    val uncheckUseCase: UncheckUseCase
        get() = UncheckUseCase(postsRepository)

    val postWithStatusMapper = PostWithStatusMapper()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}