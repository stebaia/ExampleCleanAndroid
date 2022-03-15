package com.sbaiardi.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {

    @GET("books/v1/volumes")
    suspend fun getPosts(@Query("q") author: String): Response<PostApiResponse>

}