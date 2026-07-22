package com.project.data.source.remote

import com.project.model.response.ExampleResponse
import retrofit2.http.GET

interface ExampleApiService {
    @GET("example")
    suspend fun getExample(): ExampleResponse
}
