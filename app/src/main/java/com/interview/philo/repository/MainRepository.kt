package com.interview.philo.repository

import android.util.Log
import com.interview.philo.data.remote.ApiService
import com.interview.philo.data.remote.model.SearchResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor() {
    @Inject lateinit var api: ApiService

    /**
     * Search the characters from star wars
     */
    fun search(term: String, nextPage: Int): Single<SearchResponse> {
        return api.search(term, nextPage)
            .subscribeOn(Schedulers.io())
            .doOnError{ error -> Log.e(TAG, "Error : " + error.message) }
            .map {
                return@map it
            }
    }

    companion object {
        const val TAG = "MainRepository"
    }
}