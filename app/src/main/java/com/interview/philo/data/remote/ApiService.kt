package com.interview.philo.data.remote

import com.interview.philo.data.remote.model.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("/api/people/")
    fun search(@Query("search") searchTerm: String, @Query("page") page: Int): Single<SearchResponse>
}