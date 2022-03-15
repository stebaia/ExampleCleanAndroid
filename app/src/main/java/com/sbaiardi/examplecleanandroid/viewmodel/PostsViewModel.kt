package com.sbaiardi.examplecleanandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbaiardi.domain.common.Result
import com.sbaiardi.domain.entities.Post
import com.sbaiardi.domain.usecases.CheckUseCase
import com.sbaiardi.domain.usecases.GetCheckedUseCase
import com.sbaiardi.domain.usecases.GetPostsUseCase
import com.sbaiardi.domain.usecases.UncheckUseCase
import com.sbaiardi.examplecleanandroid.entities.PostWithStatus
import com.sbaiardi.examplecleanandroid.mappers.PostWithStatusMapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostsViewModel(
    private val checkUseCase: CheckUseCase,
    private val getCheckedUseCase: GetCheckedUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val uncheckUseCase: UncheckUseCase,
    private val mapper: PostWithStatusMapper
): ViewModel() {

    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _posts = MutableLiveData<List<PostWithStatus>>()
    val posts = _posts

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _remotePosts = arrayListOf<Post>()

    fun getPosts(author: String) {
        viewModelScope.launch {
            _dataLoading.postValue(true)
            when(val postsResult = getPostsUseCase.invoke(author)){
                is Result.Success -> {
                    _remotePosts.clear()
                    _remotePosts.addAll(postsResult.data)

                    val postsCheckedFlow = getCheckedUseCase.invoke()
                    postsCheckedFlow.collect { postsChecked ->
                        posts.value = mapper.fromPostDomainToPostWithStatus(_remotePosts, postsChecked)
                        _dataLoading.postValue(false)
                    }
                }
                is Result.Error -> {
                    _dataLoading.postValue(false)
                    posts.value = emptyList()
                    _error.postValue(postsResult.exception.message)
                }
            }
        }
    }

    fun check(post: PostWithStatus) {
        viewModelScope.launch {
            checkUseCase.invoke(mapper.fromPostWithStatusToPost(post))
        }
    }

    fun uncheck(post: PostWithStatus) {
        viewModelScope.launch {
            uncheckUseCase.invoke(mapper.fromPostWithStatusToPost(post))
        }
    }


}